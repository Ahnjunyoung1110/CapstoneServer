package com.Capstone.Capstone_Server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenProvider tokenProvider;
	//커스텀 메소드 추가
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain doFilterChain) 
	throws ServletException, IOException{
		try {
			String token = parseBearerToken(httpServletRequest);
			log.info("Filter is runnung...");
			
			if(token != null && !token.equalsIgnoreCase("null")) {
				String userId = tokenProvider.validateAndGetUserId(token);
				log.info("user ID : " + userId);
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
						AuthorityUtils.NO_AUTHORITIES); // 사용자의 인증정보를 저장하고 등록
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);
			}
			
			
		}
		catch(Exception e){
			logger.error("Could not set user in security context", e);
		}
		doFilterChain.doFilter(httpServletRequest, httpServletResponse);
		
		
	}
	
	private String parseBearerToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		} else {
			return null;
		}
	}
}

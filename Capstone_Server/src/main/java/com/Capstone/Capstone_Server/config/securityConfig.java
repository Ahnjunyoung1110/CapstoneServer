package com.Capstone.Capstone_Server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Capstone.Capstone_Server.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class securityConfig{
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and() //cross origin 설정,기본으로
		.csrf().disable() // cross site request Forgery 보호 비활성화, 토큰 기반이면 비활성화 한다네요
		.httpBasic().disable()// HTTP basic 인증 비활성화, 
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //RESTFUL API 사용시 설정. 각 요청은 독립적으로 
		.authorizeHttpRequests().requestMatchers("/", "/auth/**").permitAll() //"/auth" 로 시작하면 인증없이 접근가능
		.anyRequest().authenticated(); //나머지는 인증필요
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
}

}

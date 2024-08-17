package com.Capstone.Capstone_Server.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.Capstone.Capstone_Server.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Slf4j
@Service

public class TokenProvider {
	
	private SecretKey secretKey = Jwts.SIG.HS256.key().build();
	
	public String createToken(UserEntity userEntity) {
		Date nowTime = new Date();
		Date endTime = new Date(nowTime.getTime() + 3600000);
		return Jwts.builder()
				.subject(userEntity.getId())
				.signWith(secretKey)
				.issuer("Capstone server")
				.issuedAt(nowTime)
				.expiration(endTime)
				.compact();
		
	}
	public String validateAndGetUserId(String token) {
		// Base 64로 디코딩 및 파싱
		// 서명한 후에 token의 서명과 비교
		Claims claims = Jwts.parser()//jwt 파서 생성
				.verifyWith(secretKey) // 검증할때의 비밀키 설정
				.build()
				.parseSignedClaims(token) // 받은거 파싱하고 검증 
				.getBody(); // 사용자 Id 발생시간 만료시간 받아옴 // Claim 부분 가져오기
		//유효하지 않으면 이미 예외 발생 했음
		return claims.getSubject();
		//토큰이 발행된 사용자나 Entity 식별
	}

}

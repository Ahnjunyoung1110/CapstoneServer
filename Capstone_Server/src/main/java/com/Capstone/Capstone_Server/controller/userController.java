package com.Capstone.Capstone_Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Capstone.Capstone_Server.dto.UserDTO;
import com.Capstone.Capstone_Server.model.UserEntity;
import com.Capstone.Capstone_Server.security.TokenProvider;
import com.Capstone.Capstone_Server.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("auth")
@Slf4j
public class userController {
	@Autowired
	UserService userService;
	
	@Autowired
	TokenProvider tokenProvider;
	
	//회원가입 하는 메소드
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
		try {
		if(userDTO == null || userDTO.getPassword() == null) {
			log.info("Can not be null");
		}
		
		UserEntity userEntity = UserEntity.builder()
				.username(userDTO.getUsername())
				.password(userDTO.getPassword())
				.build();
		
		UserEntity responseEntity = userService.create(userEntity);
		UserDTO responseDTO = UserDTO.builder().username(responseEntity.getUsername()).password(responseEntity.getPassword()).build();
		return ResponseEntity.ok().body(responseDTO);
		}
		catch (Exception e) {
			
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	//로그인 하는 메소드
	@PostMapping("/signin")
	public ResponseEntity<?> siginIn(@RequestBody UserDTO userDTO){
		UserEntity userEntity = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		String token =tokenProvider.createToken(userEntity);
		
		UserDTO responseDto = UserDTO.builder().token(token).build();
		return ResponseEntity.ok().body(responseDto);
	}
	
	//회원탈퇴 하는 메소드
	@DeleteMapping("/signout")
	public ResponseEntity<?> siginOut(@RequestBody UserDTO userDTO){
		UserEntity userEntity = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		UserEntity deletedEntity = userService.delete(userEntity);
		
		
		UserDTO responseDto = UserDTO.builder().id(deletedEntity.getId()).password(deletedEntity.getPassword()).build();
		return ResponseEntity.ok().body(responseDto);
	}
}

package com.Capstone.Capstone_Server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Capstone.Capstone_Server.persistence.UserRepository;
import com.Capstone.Capstone_Server.model.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public UserEntity create(UserEntity userEntity) {
		if(userEntity == null || userEntity.getUsername() == null) {
			throw new RuntimeException("There is a blank space");
		}
		if(userRepository.existsByUsername(userEntity.getUsername())) {
			log.warn("username already exits {}",userEntity.getUsername());
			throw new RuntimeException("Username already exits");
		}
		return userRepository.save(userEntity);
	}

}

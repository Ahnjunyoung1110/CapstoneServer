package com.Capstone.Capstone_Server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Capstone.Capstone_Server.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
	public boolean existsByUsername(String Username);
}

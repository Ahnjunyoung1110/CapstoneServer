package com.Capstone.Capstone_Server.model;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( uniqueConstraints  = {@UniqueConstraint(columnNames = "username")}) //username이 유니크해야하는 테이블임을 의미

public class UserEntity {
	@Id //기본키
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable = false)// 얘는 바로 아래있는것만 적용되는듯
	private String username;
	
	private String password;
	
	private enum role {Super, Hospital, collector}; //유저 권한
	
	private String authProvider; //유저 정보 제공자
}

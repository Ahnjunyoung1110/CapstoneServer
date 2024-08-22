package com.Capstone.Capstone_Server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter

//폐기물의 종류에 대한 entity
//각 종류에 따라 종류별 보관 기일 설정.
public class wasteTypeEntity {
	@Id
	String Type;
	
	int day;
	
}

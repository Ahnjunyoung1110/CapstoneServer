package com.Capstone.Capstone_Server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Capstone.Capstone_Server.model.wasteTypeEntity;
import com.Capstone.Capstone_Server.persistence.WasteTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

//폐기물 종류에 대해 정의된 WasteTypeEntity에 대한 service를 제공하는 클래스
//폐기물에 대한 종류를 추가하거나 해당하는 폐기물이 존재하는지 확인한다.
public class WasteTypeService {
	@Autowired
	WasteTypeRepository wasteTypeRepository;

	// Type를 추가하는 메소드
	public wasteTypeEntity createWasteType(String Type, int day) {
		
		if (Type == null || day == 0) {
			log.error("wastType or day cannot be null");
			throw new RuntimeException("wastType cannot be null");
		}
		// 해당 Type이 DB내에 존재하는지 확인
		final Optional<wasteTypeEntity> optional = wasteTypeRepository.findById(Type);

		if (optional.isEmpty()) { // 해당 Type이 존재하지 않을경우
			wasteTypeEntity entity = wasteTypeEntity.builder().Type(Type).day(day).build();
			return wasteTypeRepository.save(entity);
		} else { // 존재할경우
			log.error("Such type already exists");
			throw new RuntimeException("Such type already exists");
		}
	}

	// Type을 찾아 해당 Entity를 리턴하는 메소드
	public wasteTypeEntity findType(final String Type) {
		// 함수를 잘못 사용
		if (Type == null) {
			log.error("wastType cannot be null");
			throw new RuntimeException("wastType cannot be null");
		}

		// 해당 Type이 DB내에 존재하는지 확인
		final Optional<wasteTypeEntity> optional = wasteTypeRepository.findById(Type);

		if (optional.isPresent()) { // 해당 Type이 존재할 경우
			wasteTypeEntity type = optional.get();
			return type;
		} else { // 존재하지 않을경우
			log.error("Such type does not exist");
			throw new RuntimeException("Such type does not exist " + Type);
		}
	}

	// Type을 삭제하는
	public void DeleteWasteType(String Type) {
		
		if (Type == null) {
			log.error("wastType cannot be null");
			throw new RuntimeException("wastType cannot be null");
		}
		
		// 해당 Type이 DB내에 존재하는지 확인
		final Optional<wasteTypeEntity> optional = wasteTypeRepository.findById(Type);

		if (optional.isPresent()) { // 해당 Type이 존재 할 경우
			wasteTypeEntity entity = wasteTypeEntity.builder().Type(Type).build();
			wasteTypeRepository.delete(entity);
		} else { // 존재하지 않을 경우
			log.error("Such type does not exists");
			throw new RuntimeException("Such type does not exists");
		}
	}

	// 모든 Type을 return 하는 메소드
	public List<wasteTypeEntity> getAllType() {
		List<wasteTypeEntity> typeEntities = wasteTypeRepository.findAll();
		return typeEntities;
	}

}

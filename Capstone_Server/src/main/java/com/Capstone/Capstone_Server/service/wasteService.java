package com.Capstone.Capstone_Server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Capstone.Capstone_Server.model.wasteEntity;
import com.Capstone.Capstone_Server.persistence.WasteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
// wasteEntity를 생성하거나 제거하며 클래스
// 의료 폐기물의 처리를 원하는 사람이 등록하는 절차이다
public class wasteService {
	@Autowired
	WasteRepository wasteRepository;

	// waste를 생성
	public List<wasteEntity> createWaste(final wasteEntity wasteEntity) {
		validate(wasteEntity);
//		if() {
//			log.error("Such becon does not exits");
//		throw new RuntimeException("Such becon does not exits");
//		}
		wasteRepository.save(wasteEntity);
		return findAllWasteEntityByUsername(wasteEntity.getUserId());
	}

	// waste를 제거
	public List<wasteEntity> deleteWaste(final wasteEntity wasteEntity) {
		validate(wasteEntity);
		if (!wasteRepository.existsById(wasteEntity.getId())) {
			log.error("Such waste does not exists");
			throw new RuntimeException("Such waste does not exits");
		}
		wasteRepository.delete(wasteEntity);
		return findAllWasteEntityByUsername(wasteEntity.getUserId());
	}

	// waste를 업테이트
	public List<wasteEntity> updateWaste(final wasteEntity wasteEntity) {
		validate(wasteEntity);
		
		final Optional<wasteEntity> original = wasteRepository.findById(wasteEntity.getId());

		if (original.isPresent()) { // 존재 한다면
			final wasteEntity todo = original.get();
			todo.setWasteType(wasteEntity.getWasteType());
			todo.setBeacon(wasteEntity.getBeacon());
			todo.setDate(wasteEntity.getDate());
			todo.setSituation(wasteEntity.getSituation());

			wasteRepository.save(todo);
		}
		return findAllWasteEntityByUsername(wasteEntity.getUserId());
	}

	// 해당 user가 생성한 모든 waste를 return
	public List<wasteEntity> findAllWasteEntityByUsername(final String userId) {
		return wasteRepository.findByUserId(userId);
	}

	private void validate(final wasteEntity entity) {
		if (entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		if (entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user.");
		}
	}

}

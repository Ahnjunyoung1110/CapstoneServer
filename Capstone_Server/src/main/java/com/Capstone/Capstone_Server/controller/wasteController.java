package com.Capstone.Capstone_Server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Capstone.Capstone_Server.dto.ResponseDTO;
import com.Capstone.Capstone_Server.dto.wasteDTO;
import com.Capstone.Capstone_Server.model.wasteEntity;
import com.Capstone.Capstone_Server.model.wasteTypeEntity;
import com.Capstone.Capstone_Server.service.WasteTypeService;
import com.Capstone.Capstone_Server.service.wasteService;

@RestController
@RequestMapping("waste")
public class wasteController {

	@Autowired
	wasteService wasteservice;

	@Autowired
	WasteTypeService wasteTypeService;

	//해당 유저와 관련있는 폐기물을 response로 보내는 메소드
	@GetMapping
	public ResponseEntity<?> getWaste(@AuthenticationPrincipal String userId) {
		try {
			List<wasteEntity> entities = wasteservice.findAllWasteEntityByUsername(userId);
			List<wasteDTO> dtos = entities.stream().map(wasteDTO::new).collect(Collectors.toList());

			ResponseDTO<wasteDTO> responseEntity = ResponseDTO.<wasteDTO>builder().data(dtos).build();

			return ResponseEntity.ok().body(responseEntity);
		} catch (RuntimeException e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}

	}
	
	//waste를 생성하는 메소드
	//해당 유저와 관련있는 waste를 return
	@PostMapping
	public ResponseEntity<?> createWaste(@AuthenticationPrincipal String userId, @RequestBody wasteDTO DTO) {
		try {
			wasteEntity entity = wasteDTO.toEntity(DTO, wasteTypeService.findType(DTO.getWasteType()));
			entity.setId(null);
			entity.setUserId(userId);
			
			//service를 통해 waste생성
			List<wasteEntity> wasteEntities = wasteservice.createWaste(entity);

			List<wasteDTO> dtos = wasteEntities.stream().map(wasteDTO::new).collect(Collectors.toList());
			ResponseDTO<wasteDTO> responseEntity = ResponseDTO.<wasteDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(responseEntity);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//waste를 업데이트 하는 메소드
	@PutMapping
	public ResponseEntity<?> updateWaste(@AuthenticationPrincipal String userId, @RequestBody wasteDTO DTO) {
		try {
			wasteEntity entity = wasteDTO.toEntity(DTO, wasteTypeService.findType(DTO.getWasteType()));
			entity.setUserId(userId);

			List<wasteEntity> entities = wasteservice.updateWaste(entity);
			List<wasteDTO> dtos = entities.stream().map(wasteDTO::new).collect(Collectors.toList());

			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().data(dtos).build();

			return ResponseEntity.ok().body(response);
		} catch (RuntimeException e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}

	}
	
	
	//waste를 제거하는 메소드
	@DeleteMapping
	public ResponseEntity<?> deleteWaste(@AuthenticationPrincipal String userId, @RequestBody wasteDTO DTO) {
		try {
			wasteEntity entity = wasteDTO.toEntity(DTO, wasteTypeService.findType(DTO.getWasteType()));
			entity.setUserId(userId);

			List<wasteEntity> entities = wasteservice.deleteWaste(entity);
			List<wasteDTO> dtos = entities.stream().map(wasteDTO::new).collect(Collectors.toList());

			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().data(dtos).build();

			return ResponseEntity.ok().body(response);
		} catch (RuntimeException e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}

	}

}

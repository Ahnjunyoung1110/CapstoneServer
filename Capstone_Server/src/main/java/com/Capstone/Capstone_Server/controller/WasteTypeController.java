package com.Capstone.Capstone_Server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Capstone.Capstone_Server.dto.ResponseDTO;
import com.Capstone.Capstone_Server.dto.wasteDTO;
import com.Capstone.Capstone_Server.model.wasteTypeEntity;
import com.Capstone.Capstone_Server.service.WasteTypeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("WasteType")
public class WasteTypeController {
	
	@Autowired
	WasteTypeService wasteTypeService;
	
	@GetMapping
	//모든 Type을 return 하는 메소드
	public ResponseEntity<?> getAllType(@AuthenticationPrincipal String userId){
		/*
		 * 유저의 권한을 확인하는 메소드
		 * */
		List<wasteTypeEntity> responseList = wasteTypeService.getAllType();
		ResponseDTO<wasteTypeEntity> responseEntity = ResponseDTO.<wasteTypeEntity>builder().data(responseList).build();
		return ResponseEntity.ok().body(responseEntity);
	}
	
	@PostMapping
	//Type을 추가하는 메소드
	public ResponseEntity<?> CreateType(@AuthenticationPrincipal String userId, @RequestBody wasteTypeEntity wasteType){
		/*
		 * 유저의 권한을 확인하는 메소드
		 * */
		try {
			wasteTypeService.createWasteType(wasteType.getType(), wasteType.getDay());
			List<wasteTypeEntity> responseList = wasteTypeService.getAllType();
			ResponseDTO<wasteTypeEntity> responseEntity = ResponseDTO.<wasteTypeEntity>builder().data(responseList).build();
			return ResponseEntity.ok().body(responseEntity);
		}
		catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping
	//Type을 제거하는 메소드
	public ResponseEntity<?> DeleteType(@AuthenticationPrincipal String userId, @RequestBody wasteTypeEntity wasteType){
		/*
		 * 유저의 권한을 확인하는 메소드
		 * */
		try {
			wasteTypeService.DeleteWasteType(wasteType.getType());
			List<wasteTypeEntity> responseList = wasteTypeService.getAllType();
			ResponseDTO<wasteTypeEntity> responseEntity = ResponseDTO.<wasteTypeEntity>builder().data(responseList).build();
			return ResponseEntity.ok().body(responseEntity);
		}
		catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<wasteDTO> response = ResponseDTO.<wasteDTO>builder().error(error).build();

			return ResponseEntity.badRequest().body(response);
		}
	} 
	
	
	
}

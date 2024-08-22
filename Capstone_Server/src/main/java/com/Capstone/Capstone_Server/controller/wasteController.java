package com.Capstone.Capstone_Server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Capstone.Capstone_Server.dto.ResponseDTO;
import com.Capstone.Capstone_Server.dto.wasteDTO;
import com.Capstone.Capstone_Server.model.wasteEntity;
import com.Capstone.Capstone_Server.service.wasteService;
@RestController
@RequestMapping("waste")
public class wasteController {

	@Autowired
	wasteService wasteservice;

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

}

package com.Capstone.Capstone_Server.dto;

import com.Capstone.Capstone_Server.model.wasteEntity;
import com.Capstone.Capstone_Server.model.wasteTypeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class wasteDTO {
	private String id;
	
	private long beacon;
	
	private String wasteType;
	
	private String date;
	
	private String where;
	
	private String situation;
	
    public wasteDTO(wasteEntity entity) {
        this.id = entity.getId();
        this.beacon = entity.getBeacon();
        this.wasteType = entity.getWasteType().getType(); // wasteTypeEntity의 Type 필드 가져오기
        this.date = entity.getDate();
        this.where = entity.getLocation();  // `where` 필드를 `location`으로 변환
        this.situation = entity.getSituation();
        
    }
	
	public static wasteEntity toEntity(final wasteDTO dto, wasteTypeEntity wasteTypeEntity) {
		return wasteEntity.builder()
				.id(dto.id)
				.wasteType(wasteTypeEntity)
				.beacon(dto.beacon)
				.date(dto.date)
				.location(dto.where)
				.situation(dto.situation)
				.build();
	}

}

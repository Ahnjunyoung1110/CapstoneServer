package com.Capstone.Capstone_Server.model;

import org.hibernate.annotations.GenericGenerator;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "waste_entity")
public class wasteEntity {
	@Id //기본키
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "YYYYMMDD+Owncode")
	@GenericGenerator(name = "YYYYMMDD+Owncode", strategy = "com.Capstone.Capstone_Server.service.generator.wasteIdGenerator")
	private String id;
	
	@Column(nullable = false)
	long beacon;
	
	@ManyToOne
	@JoinColumn(name = "waste_type", referencedColumnName = "Type", nullable = false)
	private wasteTypeEntity wasteType;
	
	@Column(nullable = false)
	String userId;
	
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "YYYYMMDD")
	@GenericGenerator(name = "YYYYMMDD+Owncode", strategy = "com.Capstone.Capstone_Server.service.generator.DateGenerator")
	String date;
	
	@Column(nullable = false)
	String location;
	
	@Column(nullable = false)
	String situation;
}

package com.Capstone.Capstone_Server.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Capstone.Capstone_Server.model.wasteEntity;

@Repository
public interface WasteRepository extends JpaRepository<wasteEntity,String>{
	List<wasteEntity> findByUserId(String userId);
	boolean existsById(String id);
}

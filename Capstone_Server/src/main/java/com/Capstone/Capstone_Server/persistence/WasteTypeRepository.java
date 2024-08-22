package com.Capstone.Capstone_Server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Capstone.Capstone_Server.model.wasteTypeEntity;

@Repository
public interface WasteTypeRepository extends JpaRepository<wasteTypeEntity, String> {
}

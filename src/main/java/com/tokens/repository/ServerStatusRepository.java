package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tokens.models.ServerStatus;

public interface ServerStatusRepository extends JpaRepository<ServerStatus, Integer>{
	
	@Query("SELECT s FROM ServerStatus s WHERE s.systemId = :systemId")
    ServerStatus findBySystemId(String systemId);
}

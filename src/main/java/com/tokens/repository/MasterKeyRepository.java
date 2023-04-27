package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.MasterKey;

@Repository
public interface MasterKeyRepository extends JpaRepository<MasterKey, Integer>{
	
	@Query("Select m from MasterKey m where m.systemId = :systemId")
	MasterKey findMasterKeyBySystemId(@Param("systemId") String systemId);

}

package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.MasterKey;
import com.tokens.models.MasterKeyLogs;

@Repository
public interface MasterKeyLogsRepository extends JpaRepository<MasterKeyLogs,Integer>{

	@Query("Select m from MasterKeyLogs m where m.userId = :userId")
	List<MasterKeyLogs> findMasterKeyLogs(@Param("userId") int userId);
	

}

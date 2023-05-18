package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.TransactionStatusLogs;

@Repository
public interface TrasactionStatusLogsRepository extends JpaRepository<TransactionStatusLogs, Integer>{

	@Query("Select m from TransactionStatusLogs m where m.systemId = :systemId")
	List<TransactionStatusLogs> findTransactionStatusLogs(@Param("systemId") String systemId);
}

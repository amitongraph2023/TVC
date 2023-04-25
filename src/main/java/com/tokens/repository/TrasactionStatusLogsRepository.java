package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tokens.models.TransactionStatusLogs;

public interface TrasactionStatusLogsRepository extends JpaRepository<TransactionStatusLogs, Integer>{

}

package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokens.models.MasterKeyLogs;

@Repository
public interface MasterKeyLogsRepository extends JpaRepository<MasterKeyLogs,Integer>{

}

package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokens.models.MasterKey;

@Repository
public interface MasterKeyRepository extends JpaRepository<MasterKey, Integer>{

}

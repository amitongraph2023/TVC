package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tokens.models.Pos;

public interface PosRepository extends JpaRepository<Pos, Integer>{

}

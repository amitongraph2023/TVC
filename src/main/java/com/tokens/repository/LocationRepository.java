package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokens.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

}

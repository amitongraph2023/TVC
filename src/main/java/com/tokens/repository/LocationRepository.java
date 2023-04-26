package com.tokens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

	@Query("Select loc from Location loc where loc.merchantId = :merchantId")
	Optional<Location> findLocationByMerchantId(@Param("merchantId") Integer merchantId);
}

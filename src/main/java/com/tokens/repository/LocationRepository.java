package com.tokens.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Location;
import com.tokens.models.Transaction;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

	@Query(value="Select * from Location loc where loc.merchant_id = :merchantId", nativeQuery = true)
	Optional<Location> findByMerchantId(@Param("merchantId") int merchantId);
	
	@Query("SELECT lc FROM Location lc where lc.merchantId IN " 
			+ "( Select tr.merchantId FROM Transaction tr GROUP BY merchantId "
			+ "ORDER BY SUM(tr.amount) DESC)")
	List<Location> findTopLocations();
}

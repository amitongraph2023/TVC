package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

	@Query("Select loc from Location loc where loc.merchantId = :merchantId")
	Location findByMerchantId(@Param("merchantId") int merchantId);
	
	@Query("SELECT lc FROM Location lc where lc.merchantId IN " 
			+ "( Select tr.merchantId FROM Transaction tr where tr.userId = :userId GROUP BY tr.merchantId "
			+ "ORDER BY SUM(tr.amount) DESC)")
	List<Location> findTopLocations(@Param("userId") int userId);
}

package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query("SELECT tr FROM Transaction tr WHERE tr.transactionId = :transactionId")
	Transaction findByTransactionId(@Param("transactionId") Integer transactionId);
	
	@Query("Select COUNT(tr) from Transaction tr where tr.systemId = :systemId")
	int findTransactionCountofSystem(@Param("systemId") String systemId);

}

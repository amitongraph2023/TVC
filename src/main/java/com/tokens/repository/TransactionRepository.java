package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatus;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

//	amountPerLocation
//	
//	List<Transaction> getAmountPerLocation

	@Query("SELECT t FROM Transaction t WHERE t.transactionId = :transactionId")
	Transaction findByTransactionId(@Param("transactionId") Integer transactionId);
		
	@Query("SELECT t FROM Transaction t WHERE t.status = :status")
	Transaction findByStatus(@Param("status") TransactionStatus status);


}

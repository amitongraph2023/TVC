package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query("SELECT tr FROM Transaction tr WHERE tr.transactionId = :transactionId AND "
			+ "tr.id = (SELECT MIN(t.id) FROM Transaction t WHERE t.transactionId = :transactionId)")
	Transaction findByTransactionId(@Param("transactionId") String transactionId);
	
	@Query("Select COUNT(tr) from Transaction tr where tr.userId = :userId")
	int findTransactionCountofSystem(@Param("userId") int userId);
	
	@Query("Select t from Transaction t where t.userId = :userId")
	List<Transaction> findTransactionLogs(@Param("userId") int userId);

}

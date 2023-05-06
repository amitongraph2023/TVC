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
	Transaction findByTransactionId(@Param("transactionId") Integer transactionId);
	
	@Query(value = "SELECT * FROM transaction tc WHERE tc.system_id = :systemId GROUP BY tc.customer_id"
			+ "	ORDER BY SUM(tc.amount) DESC LIMIT 5", nativeQuery = true)
	List<Transaction> findTopCustomers(@Param("systemId") String systemId);
	
	@Query("Select COUNT(tr) from Transaction tr where tr.systemId = :systemId")
	int findTransactionCountofSystem(@Param("systemId") String systemId);

}

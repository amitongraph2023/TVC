package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

//	amountPerLocation
//	
//	List<Transaction> getAmountPerLocation

	Transaction findByTransactionId(Integer transactionId);


}

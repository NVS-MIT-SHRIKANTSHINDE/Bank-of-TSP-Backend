package com.TransactionService1.TransactionService1.repository;

import java.util.List;

import com.TransactionService1.TransactionService1.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


	List<Transaction> findAll();

	List<Transaction> findAllByFromAccountOrToAccount(String accountId, String accountId2);

}

package com.example.homebanking.services;

import com.example.homebanking.models.Transaction;

public interface TransactionService {

    public Transaction saveTransaction(Transaction transaction);
}

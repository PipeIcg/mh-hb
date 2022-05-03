package com.example.homebanking.services;

import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    public Loan findByName(String name);
    public List<LoanDTO> getLoanList();
    public Loan saveLoan(Loan loan);
}

package com.example.homebanking.services.implementations;

import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.Loan;
import com.example.homebanking.repositories.LoanRepository;
import com.example.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public List<LoanDTO> getLoanList() {
       return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }
}

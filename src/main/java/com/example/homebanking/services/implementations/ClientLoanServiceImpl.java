package com.example.homebanking.services.implementations;

import com.example.homebanking.models.ClientLoan;
import com.example.homebanking.repositories.ClientLoanRepository;
import com.example.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public ClientLoan saveClientLoan(ClientLoan clientLoan) {
        return clientLoanRepository.save(clientLoan);
    }
}

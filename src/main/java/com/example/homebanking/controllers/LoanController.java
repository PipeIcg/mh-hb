package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.dtos.LoanApplicationDTO;
import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @PostMapping(path = "loans")
    public ResponseEntity<Object> newLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        Client clientAutentificado = clientRepository.findByEmail(authentication.getName());
        Loan loanName = loanRepository.findByName(loanApplicationDTO.getName());
        Account accountVerification = accountRepository.findByNumber(loanApplicationDTO.getNumber());


        if (loanApplicationDTO.equals(null) ) {
            return new ResponseEntity<>("missing data", HttpStatus.FORBIDDEN);
        }
        if(loanName.equals(null)){
            return new ResponseEntity<>("Doesn't exist :'(", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() > loanName.getMaxAmount()){
            return new ResponseEntity<>("amount exceeded", HttpStatus.FORBIDDEN);
        }
        if(accountVerification.equals(null)){
            return new ResponseEntity<>("account doesn't exist", HttpStatus.FORBIDDEN);
        }
        //revisar
        if(!clientAutentificado.getAccounts().contains(accountVerification)){
            return new ResponseEntity<>("the account does not belong to the client", HttpStatus.FORBIDDEN);
        }
        double tax = loanApplicationDTO.getAmount() * 0.2 + loanApplicationDTO.getAmount();
        String description = loanApplicationDTO.getName() + "loan approved";

        ClientLoan clientLoan = new ClientLoan(tax,loanApplicationDTO.getPayments(),clientAutentificado,loanName);
        clientLoanRepository.save(clientLoan);

        Transaction loanTransaction = new Transaction(TransactionType.CREDIT,loanApplicationDTO.getAmount(),description, LocalDateTime.now(),accountVerification);
        transactionRepository.save(loanTransaction);

        accountVerification.setBalance(loanApplicationDTO.getAmount() + accountVerification.getBalance());

        return new ResponseEntity<>("successful loan", HttpStatus.CREATED);

    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoanList(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }


    @PostMapping(path = "/newLoan")
    public ResponseEntity<Object>createLoan(Authentication authentication,
    @RequestBody LoanDTO loanDTO){

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Loan loanName = loanRepository.findByName(loanDTO.getName());

        if (loanDTO.equals(null)){
            return new ResponseEntity<>("Datos vacios o nulos", HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getName().equals(loanName)){
            return new ResponseEntity<>("Loan already exist!", HttpStatus.FORBIDDEN);
        }

        Loan newLoan = new Loan(loanDTO.getName(),loanDTO.getMaxAmount(),loanDTO.getPayments(), loanDTO.getPercentage());
        loanRepository.save(newLoan);
        return new ResponseEntity<>("new loan created", HttpStatus.CREATED);
    }


    }


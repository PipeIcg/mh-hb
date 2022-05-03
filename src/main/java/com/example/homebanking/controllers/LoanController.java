package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.dtos.LoanApplicationDTO;
import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import com.example.homebanking.services.*;
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

    //service
    @Autowired
    ClientService clientService;

    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping(path = "loans")
    public ResponseEntity<Object> newLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        Client clientAutentificado = clientService.findByEmail(authentication.getName());
        Loan loanName = loanService.findByName(loanApplicationDTO.getName());
        Account accountVerification = accountService.findByNumber(loanApplicationDTO.getNumber());


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
        if(loanApplicationDTO.getAmount() <=0){
            return new ResponseEntity<>("el monto no puede ser menor o igual a 0",HttpStatus.FORBIDDEN);
        }
        //revisar
        if(!clientAutentificado.getAccounts().contains(accountVerification)){
            return new ResponseEntity<>("the account does not belong to the client", HttpStatus.FORBIDDEN);
        }
        double tax = loanApplicationDTO.getAmount() * 0.2 + loanApplicationDTO.getAmount();
        String description = loanApplicationDTO.getName() + "loan approved";

        ClientLoan clientLoan = new ClientLoan(tax,loanApplicationDTO.getPayments(),clientAutentificado,loanName);
        clientLoanService.saveClientLoan(clientLoan);

        Transaction loanTransaction = new Transaction(TransactionType.CREDIT,loanApplicationDTO.getAmount(),description, LocalDateTime.now(),accountVerification);
        transactionService.saveTransaction(loanTransaction);

        accountVerification.setBalance(loanApplicationDTO.getAmount() + accountVerification.getBalance());

        return new ResponseEntity<>("successful loan", HttpStatus.CREATED);

    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoanList(){
        return loanService.getLoanList();
    }


    @PostMapping(path = "/newLoan")
    public ResponseEntity<Object>createLoan(Authentication authentication,
    @RequestBody LoanDTO loanDTO){

        Client clientAuth = clientService.findByEmail(authentication.getName());
        Loan loanName = loanService.findByName(loanDTO.getName());

        if (loanDTO.equals(null)){
            return new ResponseEntity<>("Datos vacios o nulos", HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getName().equals(loanName)){
            return new ResponseEntity<>("Loan already exist!", HttpStatus.FORBIDDEN);
        }

        Loan newLoan = new Loan(loanDTO.getName(),loanDTO.getMaxAmount(),loanDTO.getPayments(), loanDTO.getPercentage());
        loanService.saveLoan(newLoan);
        return new ResponseEntity<>("new loan created", HttpStatus.CREATED);
    }


    }


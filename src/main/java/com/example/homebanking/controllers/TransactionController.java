package com.example.homebanking.controllers;


import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import com.example.homebanking.services.AccountService;
import com.example.homebanking.services.ClientService;
import com.example.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    //service
    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Transactional
    @RequestMapping( "/transactions")
    public ResponseEntity<Object> makeTransaction(
            Authentication authentication,
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String originAccount, @RequestParam String destinationAccount){
        Client clientAutentificado = clientService.findByEmail(authentication.getName());
        Account origen = accountService.findByNumber(originAccount);
        Account destino = accountService.findByNumber(destinationAccount);

        Set<Account> cuentas = clientAutentificado.getAccounts().stream().filter(cuenta ->cuenta.getNumber().contains(originAccount)).collect(Collectors.toSet());

        if(amount == null || originAccount.isEmpty() || description.isEmpty() || destinationAccount.isEmpty()){
            return new ResponseEntity<>("error, campos vacios ", HttpStatus.FORBIDDEN);
        }
         if(originAccount.equals(destinationAccount)){
            return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);
        }
         if(origen.getNumber() == null){
            return new ResponseEntity<>("cuenta de origen no existe",HttpStatus.FORBIDDEN);
        }
         if(destino.getNumber() == null){
            return new ResponseEntity<>("cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }
         if (cuentas.isEmpty()){
            return new ResponseEntity<>("Error",HttpStatus.FORBIDDEN);
        }
         if(origen.getBalance() < amount){
            return new ResponseEntity<>("saldo insuficiente",HttpStatus.FORBIDDEN);
        }

            double newOriginAmount = origen.getBalance() - amount;
            double newDestinationAmount = destino.getBalance() + amount;

            origen.setBalance(newOriginAmount);
            destino.setBalance(newDestinationAmount);

            Transaction debitTransaction = new Transaction(TransactionType.DEBIT,amount,description, LocalDateTime.now(),origen);
            transactionService.saveTransaction(debitTransaction);

            Transaction creditTransaction = new Transaction(TransactionType.CREDIT,amount,description,LocalDateTime.now(),destino );
            transactionService.saveTransaction(creditTransaction);

            accountService.saveAccount(origen);
            accountService.saveAccount(destino);

            return new ResponseEntity<>("created", HttpStatus.CREATED);



    }

}

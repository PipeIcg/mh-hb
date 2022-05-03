package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.services.AccountService;
import com.example.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController//escuchar y responder
@RequestMapping("/api")
public class AccountController {

    //services
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        /*return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());*/
        return accountService.getAccounts();
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        /*AccountDTO acc1 = new AccountDTO(accountRepository.findById(id).orElse(null));
        return acc1;*/
        return accountService.getAccount(id);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PostMapping(path = "clients/current/accounts")
    public ResponseEntity<Object>createAccount(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        if(client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Error 403",HttpStatus.FORBIDDEN);
        }
        else{
            Random random1 = new Random();
            Account account = new Account("VIN-"+ String.format("%08d",random1.nextInt(99999999)), LocalDateTime.now(),0,client);
            accountService.saveAccount(account);
            return new ResponseEntity<>("201 created",HttpStatus.CREATED);

        }
    }
}

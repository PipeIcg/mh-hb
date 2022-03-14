package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;




    @PostMapping(path = "/clients")
    public ResponseEntity<Object>register(
            @RequestParam String first_name, @RequestParam String last_name,
            @RequestParam String email, @RequestParam String password){
        if(first_name.isEmpty()||last_name.isEmpty()||email.isEmpty()||password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(clientRepository.findByEmail(email) != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }


        Random random1 = new Random();
        Client client1 = new Client(first_name,last_name,email,passwordEncoder.encode(password));
        clientRepository.save(client1);

        Account account1 = new Account("VIN-" + random1.nextInt(99999999), LocalDateTime.now(),0,client1);
        accountRepository.save(account1);
        return new ResponseEntity<>("Nice job!",HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        ClientDTO cdto1 = new ClientDTO(clientRepository.findByEmail(authentication.getName()));

        return cdto1;
    }

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @GetMapping("clients/{id}")

    public ClientDTO getClient(@PathVariable Long id){

        ClientDTO c1 = new ClientDTO(clientRepository.findById(id).orElse(null));
        return c1;

    }


}

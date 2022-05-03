package com.example.homebanking.services.implementations;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account) ;
    }

    @Override
    public AccountDTO getAccount(Long id) {
        AccountDTO accountDTO = new AccountDTO(accountRepository.findById(id).orElse(null));
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}

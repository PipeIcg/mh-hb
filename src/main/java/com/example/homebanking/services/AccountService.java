package com.example.homebanking.services;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);
    public AccountDTO getAccount(Long id);
    public List<AccountDTO> getAccounts();
    public Account findByNumber(String number);

}

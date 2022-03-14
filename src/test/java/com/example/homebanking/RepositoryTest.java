package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan>loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonaLoan(){
        List<Loan>loans= loanRepository.findAll();
        assertThat(loans,hasItem(hasProperty("name",is("Personal"))));
    }
//test account
    @Test
    public void existAccounts(){
        List<Account>accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void existCreationDate(){
        List<Account>accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("creationDate")));
    }
    //test card
    @Test
    public void existCards(){
        List<Card>cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    public void existCvv(){
        List<Card>cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cvv" )));
    }

    //Client
    @Test
    public void clientExist(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }

    @Test
    public void existClientloan(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,hasItem(hasProperty("clientLoans")));
    }

    //transaction
    @Test
    public void transactionExist(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void accountInTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("account")));
    }
}

package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime tomorrow = today.minusDays(1);
			LocalDateTime fiveYears = today.plusYears(5);



			//Clientes
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("1234"));
			clientRepository.save(client1);
			Client client2 = new Client("Felipe","Castro","felipe91.c@gmail.com", passwordEncoder.encode("1234"));
			clientRepository.save(client2);
			Client client3 = new Client("Diego", "Castro", "dc.val@gmail.com", passwordEncoder.encode("123123"));
			clientRepository.save(client3);
			Client admin = new Client("admin","admin","admin@admin.com", passwordEncoder.encode("1234"));
			clientRepository.save(admin);

			//Cuentas
			Account account1 = new Account("VIN001",today,5000,client1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002",tomorrow,7500,client1);
			accountRepository.save(account2);
			Account account3 = new Account("VIN003",today,10000,client2);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004",today,100000,client3);
			accountRepository.save(account4);



			/*Transacciones*/
			//cliente1
			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -2000, "pago", LocalDateTime.now(),account1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -4000, "celular", LocalDateTime.now(), account1);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 10000,"salida finde", LocalDateTime.now(),account2);
			transactionRepository.save(transaction3);

			//cliente2
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 20000, "formateo compu",LocalDateTime.now(), account3);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -5000, "supermercado",LocalDateTime.now(), account3);
			transactionRepository.save(transaction5);

			//Cliente3
			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -4000,"pago clinica", LocalDateTime.now(), account4);
			transactionRepository.save(transaction6);

			Transaction transaction7 = new Transaction(TransactionType.CREDIT, 9000,"venta bici",LocalDateTime.now(),account4);
			transactionRepository.save(transaction7);

			Loan loan1 = new Loan("Mortgage",500000, List.of(12,24,36,48,60),20);
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal",100000,List.of(6,12,24),20);
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotive",300000, List.of(6,12,24,36),20);
			loanRepository.save(loan3);

			//Prestamos Melba
			ClientLoan clientLoan1 = new ClientLoan(400000,loan1.getPayments().get(4),client1,loan1);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(50000,loan2.getPayments().get(1),client1,loan2);
			clientLoanRepository.save(clientLoan2);

			//Prestamos cliente2
			ClientLoan clientLoan3 = new ClientLoan(100000,loan2.getPayments().get(2),client2,loan2);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(200000,loan3.getPayments().get(3),client2,loan3);
			clientLoanRepository.save(clientLoan4);

			//CARDS
			Card card1 = new Card("Melba Morel",CardType.DEBIT,CardColor.GOLD,"4321 1234 3214 4123",777,LocalDateTime.now(),fiveYears,true,client1);
			cardRepository.save(card1);

			Card card2 = new Card("Melba Morel",CardType.CREDIT,CardColor.TITANIUM, "0303 4560 3034 5603", 432,LocalDateTime.now(), fiveYears,true,client1);
			cardRepository.save(card2);

			Card card3 = new Card(client2.getFirst_name() + " " + client2.getLast_name(),CardType.CREDIT, CardColor.SILVER,"5454 4545 0045 5400",968,LocalDateTime.now(),fiveYears,true,client2);
			cardRepository.save(card3);

			System.out.println(clientRepository.findByEmail("melba@mindhub.com").getAccounts().size());
		};
	}
}

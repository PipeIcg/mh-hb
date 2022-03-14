package com.example.homebanking.controllers;

import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object>createCard(Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color
            ){

        Client client = clientRepository.findByEmail(authentication.getName());
        List<Card> cardList = client.getCards().stream().filter(card -> card.getType() == type).collect(Collectors.toList());
        List<Card> cardListTrue = cardList.stream().filter(card -> card.isState()).collect(Collectors.toList());
        Random random = new Random();
        String uno = random.nextInt(9999) + "";
        String dos = random.nextInt(8888)+ "";
        String tres = random.nextInt(7777)+ "";
        String cuatro = random.nextInt(6666)+ "";
        LocalDateTime fiveYears = LocalDateTime.now().plusYears(5);

        //String Card number refactor
        String cardNumber = CardUtils.getCardNumber();
        
        //random cvv
        int cvv = CardUtils.getCvv();


        if (cardListTrue.size() >=3){
            return new ResponseEntity<>("Error, you only can register 3 cards! ", HttpStatus.FORBIDDEN);
        }
        else{
            Card card = new Card(client.getFirst_name() + " " + client.getLast_name(),type,color,cardNumber ,cvv, LocalDateTime.now(),fiveYears,true,client);
            cardRepository.save(card);
            return new ResponseEntity<>("201 Nice job!", HttpStatus.CREATED);
        }

    }

    @PatchMapping(path = "/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        Card card = cardRepository.findById(id).orElse(null);

        card.setState(false);
        cardRepository.save(card);
        return new ResponseEntity<>("Deleted!",HttpStatus.CREATED);

    }


}

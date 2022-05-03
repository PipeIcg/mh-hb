package com.example.homebanking.services;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Card;

public interface CardService {
    public Card saveCard(Card card);
    public Card findById(Long id);
}

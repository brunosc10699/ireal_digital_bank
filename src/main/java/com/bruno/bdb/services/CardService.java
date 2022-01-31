package com.bruno.bdb.services;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.domain.Card;

public interface CardService {

    Card save(Account account);

    Card findByNumber(String cardNumber);

    Card findById(Long cardId);

    void activate();

    void receiptConfirmation();

    void sendCard(Long cardId);
}

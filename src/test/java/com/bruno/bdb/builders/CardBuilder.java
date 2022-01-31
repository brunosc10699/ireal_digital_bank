package com.bruno.bdb.builders;

import com.bruno.bdb.domain.Card;

public class CardBuilder {

    public static Card card() {
        return Card.builder()
                .id(AccountBuilder.account().getId())
                .number("2201 2045 0012 7652")
                .password("7581")
                .expirationDate("04-22")
                .name("Anderson S Silva")
                .cvv("154")
                .status(1)
                .build();
    }
}

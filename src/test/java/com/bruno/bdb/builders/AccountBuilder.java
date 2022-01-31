package com.bruno.bdb.builders;

import com.bruno.bdb.domain.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountBuilder {

    public static Account account() {

        return Account.builder()
                .id("1")
                .branch(BranchBuilder.branch())
                .holder(HolderBuilder.holder())
                .card(CardBuilder.card())
                .checkDigit("1")
                .balance(BigDecimal.valueOf(0d))
                .openingDate(LocalDateTime.of(2022, 1, 20, 15, 45))
                .type(0)
                .status(1)
                .build();
    }
}

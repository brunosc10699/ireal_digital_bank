package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Account;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "account")
@Builder
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;

    private String firstName;

    private String surName;

    private BigDecimal balance;

    public static AccountDTO fromEntity(Account account) {
        return AccountDTO.builder()
                .account(account.getId() + "-" + account.getCheckDigit())
                .firstName(account.getHolder().getFirstName())
                .surName(account.getHolder().getSurName())
                .balance(account.getBalance())
                .build();
    }
}

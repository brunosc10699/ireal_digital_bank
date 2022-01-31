package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonTypeName("deposit")
public class DepositDTO extends TransactionDTO {

    private static final long serialVersionUID = 1L;

    private String test;

    @Override
    public TransactionDTO fromEntity(Transaction transaction) {
        return null;
    }
}

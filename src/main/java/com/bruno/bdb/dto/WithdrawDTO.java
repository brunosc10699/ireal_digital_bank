package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Transaction;
import com.bruno.bdb.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonTypeName("withdraw")
public class WithdrawDTO extends TransactionDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Enter the card number.")
    @Length(message = "The card number must have 16 numbers.", min = 16, max = 16)
    private String cardNumber;

    @NotNull(message = "Enter the name as written on the card")
    private String cardName;

    @NotNull(message = "Enter card expiration date: e.g. mm-yy")
    private String expirationDate;

    @NotNull(message = "Enter the three-digit security code")
    @Length(message = "The three-digit security code must have 3 numbers.", min = 3, max = 3)
    private String cvv;

    @NotNull(message = "Enter the card's four-digit password")
    @Length(message = "The card password must have 4 numbers.", min = 4, max = 4)
    private String cardPassword;

    @NotNull(message = "Enter terminal id")
    private String terminalId;

    @Override
    public TransactionDTO fromEntity(Transaction transaction) {
        return WithdrawDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .accountDTO(AccountDTO.fromEntity(transaction.getAccount()))
                .status(TransactionStatus.toEnum(transaction.getStatus()).getDescription())
                .terminalId(terminalId)
                .build();
    }
}

package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Transaction;
import com.bruno.bdb.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonTypeName("payment")
public class PaymentDTO extends TransactionDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Enter the payment code.")
    private String paymentCode;

    @Override
    public TransactionDTO fromEntity(Transaction transaction) {
        return PaymentDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .accountDTO(AccountDTO.fromEntity(transaction.getAccount()))
                .paymentCode(paymentCode)
                .status(TransactionStatus.toEnum(transaction.getStatus()).getDescription())
                .build();
    }

}

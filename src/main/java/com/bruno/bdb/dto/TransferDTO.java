package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonTypeName("transfer")
public class TransferDTO extends TransactionDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Enter the number of the destination bank branch.")
    private Integer destinationBranchNumber;

    @NotNull(message = "Enter the destination account number and check digit.")
    private Long destinationAccountNumber;

    @NotNull(message = "Enter the destination account check digit.")
    private Integer destinationAccountCheckDigit;

    @NotBlank(message = "Enter the beneficiary's cpf.")
    private String cpf;

    @Override
    public TransactionDTO fromEntity(Transaction transaction) {
        return null;
    }
}

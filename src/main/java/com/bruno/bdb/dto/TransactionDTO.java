package com.bruno.bdb.dto;

import com.bruno.bdb.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "Enter the transaction amount.")
    @Min(message = "Transaction amount must be IR$ 1.00 at least", value = 1)
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime transactionDate;

    private AccountDTO accountDTO;

    public abstract TransactionDTO fromEntity(Transaction transaction);
}

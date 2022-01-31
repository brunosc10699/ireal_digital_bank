package com.bruno.bdb.builders;

import com.bruno.bdb.dto.TransactionDTO;
import com.bruno.bdb.dto.TransferDTO;

import java.math.BigDecimal;

public class TransferDTOBuilder {

    public static TransactionDTO transferDTO() {
        return TransferDTO.builder()
                .id("1")
                .amount(BigDecimal.valueOf(100d))
                .build();
    }
}

package com.bruno.bdb.builders;

import com.bruno.bdb.dto.NewHolderDTO;

import java.math.BigDecimal;

public class NewUserDTOBuilder {

    public static NewHolderDTO customerDTO() {
        return NewHolderDTO.builder()
                .id("79469840062")
                .firstName("Anderson Spider")
                .surName("Silva")
                .email("spidersilva@gmail.com")
                .accountPassword("1@Aa23456")
                .income(BigDecimal.valueOf(30000d))
                .build();
    }
}

package com.bruno.bdb.builders;

import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HolderBuilder {

    public static Holder holder() {

        return Holder.builder()
                .id("1")
                .firstName("Anderson Spider")
                .surName("Silva")
                .email("anderson@gmail.com")
                .income(BigDecimal.valueOf(15000d))
                .signUpDate(LocalDateTime.of(2022, 01, 20, 00, 00))
                .status(Status.ACTIVE.getCode())
                .build();
    }
}

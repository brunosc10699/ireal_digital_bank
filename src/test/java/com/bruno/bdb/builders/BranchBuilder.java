package com.bruno.bdb.builders;

import com.bruno.bdb.domain.Branch;

import java.time.LocalDateTime;

public class BranchBuilder {

    public static Branch branch() {
       return Branch.builder()
               .id(1)
               .name("São Paulo")
               .openingDate(LocalDateTime.of(2022, 01, 20, 8, 0))
               .status(1)
               .build();
    }
}

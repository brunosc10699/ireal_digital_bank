package com.bruno.bdb.config;

import com.bruno.bdb.dto.BranchDTO;
import com.bruno.bdb.dto.NewHolderDTO;
import com.bruno.bdb.enums.Status;
import com.bruno.bdb.services.BranchService;
import com.bruno.bdb.services.HolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final Environment environment;

    private final BranchService branchService;

    private final HolderService holderService;

    @Override
    public void run(String... args) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {

            BranchDTO branchDTO1 = BranchDTO.builder()
                    .name("Porto Alegre")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO1);

            BranchDTO branchDTO2 = BranchDTO.builder()
                    .name("Goiânia")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO2);

            BranchDTO branchDTO3 = BranchDTO.builder()
                    .name("Belém")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO3);

            BranchDTO branchDTO4 = BranchDTO.builder()
                    .name("Fortaleza")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO4);

            BranchDTO branchDTO5 = BranchDTO.builder()
                    .name("Recife")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO5);

            BranchDTO branchDTO6 = BranchDTO.builder()
                    .name("Salvador")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO6);

            BranchDTO branchDTO7 = BranchDTO.builder()
                    .name("Belo Horizonte")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO7);

            BranchDTO branchDTO8 = BranchDTO.builder()
                    .name("Rio de Janeiro")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO8);

            BranchDTO branchDTO9 = BranchDTO.builder()
                    .name("São Paulo")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO9);

            BranchDTO branchDTO10 = BranchDTO.builder()
                    .name("Curitiba")
                    .openingDate(LocalDateTime.now())
                    .status(Status.ACTIVE.getCode())
                    .build();
            branchService.save(branchDTO10);

            NewHolderDTO holderDTO = NewHolderDTO.builder()
                    .id("79469840062")
                    .firstName("Anderson Spider")
                    .surName("Silva")
                    .email("spidersilva@gmail.com")
                    .accountPassword("1@Aa23456")
                    .income(BigDecimal.valueOf(15000d))
                    .build();

            holderService.save(holderDTO);
        }
    }
}

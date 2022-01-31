package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.Branch;
import com.bruno.bdb.dto.BranchDTO;
import com.bruno.bdb.enums.Status;
import com.bruno.bdb.repositories.BranchRepository;
import com.bruno.bdb.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public Branch save(BranchDTO branchDTO) {
        return branchRepository.save(branchFromDTO(branchDTO));
    }

    public Branch branchFromDTO(BranchDTO branchDTO) {
        return Branch.builder()
                .name(branchDTO.getName())
                .openingDate(LocalDateTime.now())
                .status(Status.ACTIVE.getCode())
                .build();
    }
}

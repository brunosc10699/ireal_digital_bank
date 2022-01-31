package com.bruno.bdb.services;

import com.bruno.bdb.domain.Branch;
import com.bruno.bdb.dto.BranchDTO;

public interface BranchService {

    Branch save(BranchDTO branchDTO);
}

package com.bruno.bdb.services;

import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.dto.NewHolderDTO;

public interface HolderService {

    Holder save(NewHolderDTO newHolderDTO);
}

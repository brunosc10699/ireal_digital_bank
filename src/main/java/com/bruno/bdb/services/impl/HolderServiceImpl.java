package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.dto.NewHolderDTO;
import com.bruno.bdb.enums.Status;
import com.bruno.bdb.repositories.HolderRepository;
import com.bruno.bdb.services.AccountService;
import com.bruno.bdb.services.HolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HolderServiceImpl implements HolderService {

    private final HolderRepository holderRepository;

    private final AccountService accountService;

    @Override
    @Transactional
    public Holder save(NewHolderDTO newHolderDTO) {
        Holder holder = holderRepository.save(holderFromDTO(newHolderDTO));
        getNewAccount(holder, newHolderDTO.getAccountPassword());
        return holder;
    }

    private Holder holderFromDTO(NewHolderDTO newHolderDTO) {
        return Holder.builder()
                .id(newHolderDTO.getId())
                .firstName(newHolderDTO.getFirstName())
                .surName(newHolderDTO.getSurName())
                .email(newHolderDTO.getEmail())
                .income(newHolderDTO.getIncome())
                .signUpDate(LocalDateTime.now())
                .status(Status.ACTIVE.getCode())
                .build();
    }

    public void getNewAccount(Holder holder, String password) {
        accountService.save(holder, password);
    }
}

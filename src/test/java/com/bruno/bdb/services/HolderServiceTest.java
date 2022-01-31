package com.bruno.bdb.services;

import com.bruno.bdb.repositories.HolderRepository;
import com.bruno.bdb.services.impl.AccountServiceImpl;
import com.bruno.bdb.services.impl.HolderServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HolderServiceTest {

    @Mock
    private HolderRepository holderRepository;

    @InjectMocks
    private HolderServiceImpl holderService;

    @InjectMocks
    private AccountServiceImpl accountService;

}

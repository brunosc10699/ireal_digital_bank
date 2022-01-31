package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.enums.CardStatus;
import com.bruno.bdb.enums.Status;
import com.bruno.bdb.security.SpringSecurityAccount;
import com.bruno.bdb.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDetailServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Account account = accountService.findById(accountNumber);
        if (account == null) {
            log.info("Incorrect login attempt with account number + '" + accountNumber + "'.");
            throw new UsernameNotFoundException("Account '" + accountNumber + "' not found!");
        }
        return SpringSecurityAccount.builder()
                .id(account.getId())
                .checkDigit(account.getCheckDigit())
                .password(account.getPassword())
                .holderFirstName(account.getHolder().getFirstName())
                .balance(account.getBalance())
                .accountStatus(Status.toEnum(account.getStatus()))
                .cardStatus(CardStatus.toEnum(account.getCard().getStatus()))
                .build();
    }
}

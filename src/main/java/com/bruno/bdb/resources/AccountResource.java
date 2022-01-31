package com.bruno.bdb.resources;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.dto.AccountDTO;
import com.bruno.bdb.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;

    @GetMapping(value = "/{accountNumber}-{checkDigit}")
    public ResponseEntity<AccountDTO> findById(@Valid @PathVariable String accountNumber, @PathVariable String checkDigit) {
        String bankAccount = accountNumber + "-" + checkDigit;
        Account account = accountService.findById(bankAccount);
        return ResponseEntity.ok(AccountDTO.fromEntity(account));
    }
}

package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.domain.Branch;
import com.bruno.bdb.domain.Card;
import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.repositories.AccountRepository;
import com.bruno.bdb.services.AccountService;
import com.bruno.bdb.services.CardService;
import com.bruno.bdb.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CardService cardService;

    @Override
    @Transactional
    public Account save(Holder holder, String password) {
        return accountRepository.save(getNewAccount(holder, password));
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(String account) {
        String[] accountArray = account.split("-");
        Long accountNumber = Long.parseLong(accountArray[0]);
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ObjectNotFoundException("This account '" + accountNumber + "' does not exist."));
    }

    @Override
    @Transactional
    public Account updateBalance(Account account) {
        return accountRepository.save(account);
    }

    private Account getNewAccount(Holder holder, String password) {
        Account account = Account.builder()
                .checkDigit(generateCheckDigit(holder.getId()))
                .password(new BCryptPasswordEncoder().encode(password))
                .balance(BigDecimal.valueOf(0d))
                .openingDate(LocalDateTime.now())
                .type(0)
                .status(1)
                .branch(getBranchByUserId(holder.getId()))
                .holder(holder)
                .build();
        Card card = getNewCard(account);
        account.setCard(card);
        return account;
    }

    /**
     * To generate the check digit, use the following rule:
     * 1) Add the eleven digits of the cpf
     * 2) Divide the sum value by the number of digits
     * 3) Consider as the check digit of the account, the
     * absolute value of the result of the division.
     * e.g., cpf 01234567890
     *          sum of digits = 45
     *          division of the sum by 11 = 4
     *          check digit = 4
     *
     * @param cpf holder identifier
     * @return String
     */
    private Integer generateCheckDigit(String cpf) {
        int checkDigit = 0;
        for (int i = 0; i < cpf.length(); i++) {
            checkDigit += Integer.parseInt(cpf.substring(i, i + 1));
        }
        return checkDigit / cpf.length();
    }

    private Card getNewCard(Account account) {
        return cardService.save(account);
    }

    private Branch getBranchByUserId(String cpf) {
        switch (Integer.parseInt(cpf.substring(8, 9))) {
            case 0:
                return Branch.builder().id(1).build();
            case 1:
                return Branch.builder().id(2).build();
            case 2:
                return Branch.builder().id(3).build();
            case 3:
                return Branch.builder().id(4).build();
            case 4:
                return Branch.builder().id(5).build();
            case 5:
                return Branch.builder().id(6).build();
            case 6:
                return Branch.builder().id(7).build();
            case 7:
                return Branch.builder().id(8).build();
            case 9:
                return Branch.builder().id(10).build();
            default:
                return Branch.builder().id(9).build();
        }
    }
}

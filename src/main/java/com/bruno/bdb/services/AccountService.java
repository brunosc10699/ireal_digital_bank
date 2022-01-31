package com.bruno.bdb.services;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.security.SpringSecurityAccount;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AccountService {

    Account save(Holder holder, String password);

    Account findById(String accountNumber);

    Account updateBalance(Account account);

    public static SpringSecurityAccount getAuthenticated() {
        try {
            return (SpringSecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}

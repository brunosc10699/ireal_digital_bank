package com.bruno.bdb.security;

import com.bruno.bdb.enums.CardStatus;
import com.bruno.bdb.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpringSecurityAccount implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String id;
    private String checkDigit;
    private String password;
    private String holderFirstName;
    private BigDecimal balance;
    private Status accountStatus;
    private CardStatus cardStatus;
    private Collection<? extends GrantedAuthority> authorities;

    public String getId() {
        return id;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    @Override
    public String getUsername() {
        return id + "-" + checkDigit;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getHolderFirstName() {
        return holderFirstName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Status getAccountStatus() {
        return accountStatus;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getAccountStatus() == Status.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
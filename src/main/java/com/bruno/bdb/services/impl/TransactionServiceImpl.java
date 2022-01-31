package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.*;
import com.bruno.bdb.dto.PaymentDTO;
import com.bruno.bdb.dto.TransactionDTO;
import com.bruno.bdb.dto.TransferDTO;
import com.bruno.bdb.dto.WithdrawDTO;
import com.bruno.bdb.enums.CardStatus;
import com.bruno.bdb.enums.TransactionStatus;
import com.bruno.bdb.repositories.TransactionRepository;
import com.bruno.bdb.security.SpringSecurityAccount;
import com.bruno.bdb.services.AccountService;
import com.bruno.bdb.services.CardService;
import com.bruno.bdb.services.TransactionService;
import com.bruno.bdb.services.exceptions.AuthorizationException;
import com.bruno.bdb.services.exceptions.CardDataException;
import com.bruno.bdb.services.exceptions.InsufficientFundsException;
import com.bruno.bdb.services.exceptions.InvalidPaymentAmountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bruno.bdb.enums.CardStatus.toEnum;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final CardService cardService;

    @Override
    @Transactional
    public Transaction transfer(TransferDTO transferDTO) {
        return null;
    }

    @Override
    @Transactional
    public Transaction payment(PaymentDTO paymentDTO) {
        Account account = checkAuthentication();
        checkBalance(account, paymentDTO);
        checkPaymentAmount(paymentDTO);
        Transaction payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .amount(paymentDTO.getAmount())
                .transactionDate(LocalDateTime.now())
                .account(account)
                .code(paymentDTO.getPaymentCode())
                .status(TransactionStatus.SUCCESS.getCode())
                .build();
        return finishTransaction(payment);
    }

    @Override
    public Transaction withdraw(WithdrawDTO withdrawDTO) {
        Card card = checkCardAuthenticity(withdrawDTO);
        checkBalance(card.getAccount(), withdrawDTO);
        Transaction withdraw = Withdraw.builder()
                .id(UUID.randomUUID().toString())
                .amount(withdrawDTO.getAmount())
                .transactionDate(LocalDateTime.now())
                .account(card.getAccount())
                .status(TransactionStatus.SUCCESS.getCode())
                .terminalId(withdrawDTO.getTerminalId())
                .build();
        return finishTransaction(withdraw);
    }

    private Account checkAuthentication() {
        SpringSecurityAccount springSecurityAccount = AccountService.getAuthenticated();
        if (springSecurityAccount == null) {
            log.info("Attempt to make a transaction without been authenticated.");
            throw new AuthorizationException("Forbidden");
        }
        return Account.builder()
                .id(springSecurityAccount.getId())
                .checkDigit(springSecurityAccount.getCheckDigit())
                .balance(springSecurityAccount.getBalance())
                .status(springSecurityAccount.getAccountStatus().getCode())
                .build();
    }

    private void checkPaymentAmount(TransactionDTO paymentDTO) {
        if (paymentDTO.getAmount().compareTo(BigDecimal.valueOf(0d)) <= 0d)
            throw new InvalidPaymentAmountException("Payment amount invalid: 'IR$ " + paymentDTO.getAmount() + "'");
    }

    private void checkBalance(Account account, TransactionDTO transactionDTO) {
        if (account.getBalance().compareTo(transactionDTO.getAmount()) < 0)
            throw new InsufficientFundsException("Insufficient funds! Your account balance is 'IR$ " + account.getBalance() + "'.");
    }

    private Card checkCardAuthenticity(WithdrawDTO withdrawDTO) {
        Card card = cardService.findByNumber(withdrawDTO.getCardNumber());
        if (card.getStatus() == CardStatus.BUILT.getCode()) {
            log.warn(
                    "Database violation! Attempt to withdraw with a card number not yet sent. " +
                    "Card number: '" + withdrawDTO.getCardNumber() + "'. " +
                    "Terminal id: '" + withdrawDTO.getTerminalId() + "'."
            );
        }
        if (card.getStatus() != CardStatus.ACTIVE.getCode()) {
            log.warn("Attempt to use the card '" + withdrawDTO.getCardNumber() + "'. " +
                    "It's status is " + toEnum(card.getStatus()).getDescription());
            throw new CardDataException("This card '" + withdrawDTO.getCardNumber() + "' is " + toEnum(card.getStatus()).getDescription());
        }
        if (
                !encrypt(withdrawDTO.getCardName()).equals(card.getName()) ||
                !encrypt(withdrawDTO.getExpirationDate()).equals(card.getExpirationDate()) ||
                !encrypt(withdrawDTO.getCvv()).equals(card.getCvv()) ||
                !encrypt(withdrawDTO.getCardPassword()).equals(card.getPassword())
        ) throw new CardDataException("Please, review card details!");
        return card;
    }

    public Transaction finishTransaction(Transaction transaction) {
        transaction = transactionRepository.save(transaction);
        transaction.getAccount().setBalance(transaction.getAccount().getBalance().subtract(transaction.getAmount()));
        accountService.updateBalance(transaction.getAccount());
        return transaction;
    }

    private String encrypt(String data) {
        return new BCryptPasswordEncoder().encode(data);
    }

    private Account checkExistingAccount(String accountNumber, Integer checkDigit) {
        return null;
    }
}

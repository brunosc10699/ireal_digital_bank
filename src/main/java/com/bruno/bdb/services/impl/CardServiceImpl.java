package com.bruno.bdb.services.impl;

import com.bruno.bdb.domain.Account;
import com.bruno.bdb.domain.Card;
import com.bruno.bdb.domain.Holder;
import com.bruno.bdb.enums.CardStatus;
import com.bruno.bdb.repositories.CardRepository;
import com.bruno.bdb.security.SpringSecurityAccount;
import com.bruno.bdb.services.AccountService;
import com.bruno.bdb.services.CardService;
import com.bruno.bdb.services.exceptions.AuthorizationException;
import com.bruno.bdb.services.exceptions.CardDataException;
import com.bruno.bdb.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.bruno.bdb.constants.Constant.*;
import static com.bruno.bdb.enums.CardStatus.toEnum;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public Card save(Account account) {
        Card card = generateNewCard(account);
        return cardRepository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public Card findByNumber(String cardNumber) {
        return cardRepository.findByNumber(cardNumber)
                .orElseThrow(() -> new ObjectNotFoundException("Invalid card number : '" + cardNumber + "'"));
    }

    @Override
    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ObjectNotFoundException("Invalid card id : '" + cardId + "'"));
    }

    @Override
    @Transactional
    public void activate() {
        Account account = checkAuthentication();
        Card card = findById(account.getId());
        if (card.getStatus() != CardStatus.RECEIVED.getCode()) {
            log.info("Attempt to activate card number '" + card.getNumber() + "'. " +
                    "The card status was '" + toEnum(card.getStatus()).getDescription() + "'");
            throw new CardDataException("Waiting for confirmation of receipt of the card.");
        }
        card.setStatus(CardStatus.ACTIVE.getCode());
        cardRepository.save(card);
        log.info("Card number '" + card.getNumber() + "' activated");
    }

    @Override
    public void receiptConfirmation() {
        Account account = checkAuthentication();
        Card card = findById(account.getId());
        if (card.getStatus() != CardStatus.SENT.getCode()) {
            log.info("Unsuccessful attempt to confirm receipt of card number: '" + card.getNumber() + "'. " +
                    "The card status was '" + toEnum(card.getStatus()).getDescription() + "'");
            throw new CardDataException("Check logs");
        }
        card.setStatus(CardStatus.RECEIVED.getCode());
        cardRepository.save(card);
        log.info("Card number '" + card.getNumber() + "' received");
    }

    @Override
    public void sendCard(Long cardId) {
        Card card = findById(cardId);
        card.setStatus(CardStatus.SENT.getCode());
        card.setCvv(encrypt(card.getCvv()));
        card.setNumber(encrypt(card.getNumber()));
        card.setPassword(encrypt(card.getPassword()));
        cardRepository.save(card);
        log.info("Card number '" + card.getNumber() + "' sent");
    }

    private Card generateNewCard(Account account) {
        return Card.builder()
                .number(generateCardNumber(account.getHolder()))
                .password(generateCardSecret(9999, 4))
                .expirationDate(getCardExpirationDate())
                .name(generateCardName(account.getHolder()))
                .cvv(generateCardSecret(999, 3))
                .status(CardStatus.BUILT.getCode())
                .account(account)
                .build();
    }

    /**
     * Rule for creating the card number:
     *
     * 1st part) The first six digits represent the Ireal bank: 220120;
     *
     * 2nd Part) The next three digits are composed of the sum of the
     * eleven digits of the customer's CPF, multiplied by a random number
     * from 0 to 9, followed by zeros to complete the number of digits,
     * if necessary;
     *
     * 3rd part) The next four digits are made by joining the hour and
     * minutes when creating the number.
     *
     * 4th part) The next two digits are composed of the complement
     * of "10" for the fifth and sixth digits of the customer's cpf.
     * Ex: cpf 01234567890
     *       - fifth and sixth digits: 4 5
     *       - 10's complement: 6 5
     *
     * 5th part) The last digit is the check digit, which is composed of
     * the "MOD" of the first 15 digits of the new card by the total
     * digits of a card, which is currently 16.
     * e.g., new card without the check digit: 0123 4567 8901 234
     *       - sum of the 15 digits of the new card = 55
     *       - division of the sum by 16 = 7
     *       - new card: 0123 4567 8901 234 7
     *
     * If the result is a value to two decimal places, the digits are
     * added to obtain the check digit.
     * e.g., new card without the check digit: 2201 2022 5101 165
     *       - sum of the 15 digits of the new card = 30
     *       - division of the sum by 16 = 14
     *       - add 14 = 1 + 4 = 5
     *       - new card: 2201 2022 5101 165 5
     */
    private String generateCardNumber(Holder holder) {
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append(IREAL_BANK_NUMBER);
        cardNumber.append(getCardNumberSecondPart(holder.getId()));
        cardNumber.append(getCardNumberThirdPart());
        cardNumber.append(getCardNumberFourthPart(holder.getId()));
        cardNumber.append(getCheckDigit(cardNumber));
        return cardNumber.toString();
    }

    private String generateCardSecret(int bound, int length) {
        StringBuilder result = new StringBuilder(String.valueOf(new Random().nextInt(bound)));
        while (result.length() < length) result.append("0");
        return result.toString();
    }

    private String getCardNumberSecondPart(String cpf) {
        int sum = 0;
        for (int i = 0; i < cpf.length(); i++) {
            sum += Integer.parseInt(cpf.substring(i, i + 1));
        }
        String result = String.valueOf(sum * new Random().nextInt(9));
        if (result.length() == 3) return result;
        if (result.length() == 1) return result + "00";
        return result + "0";
    }

    private String getCardNumberThirdPart() {
        String hour = String.valueOf(LocalTime.now().getHour());
        if (hour.length() == 1) hour = "0" + hour;

        String minutes = String.valueOf(LocalTime.now().getMinute());
        if (minutes.length() == 1) minutes = "0" + minutes;

        return hour + minutes;
    }

    private String getCardNumberFourthPart(String cpf) {
        String fifthChar = String.valueOf(10 - Integer.parseInt(cpf.substring(4, 5)));
        String sixthChar = String.valueOf(10 - Integer.parseInt(cpf.substring(5, 6)));
        return fifthChar + sixthChar;
    }

    private String getCheckDigit(StringBuilder cardNumber) {
        int sum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
        }
        int result = sum % CARD_NUMBER_LENGTH;
        if (result > 9) {
            result = (result % 10) + (result / 10);
        }
        return String.valueOf(result);
    }

    private String getCardExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yy");
        LocalDate now = LocalDate.now();
        now = now.plusYears(CARD_EXPIRATION_TIME);
        return now.format(formatter);
    }

    private String generateCardName(Holder holder) {
        List<String> prepositions = Arrays.asList("da", "das", "de", "del", "di", "do", "dos", "du", "dus", "von");

        List<String> names = new ArrayList<>() {{
            addAll(Arrays.asList(holder.getFirstName().split(" ")));
            addAll(Arrays.asList(holder.getSurName().split(" ")));
        }};

        StringBuilder cardName = new StringBuilder();
        cardName.append(names.get(0));

        for (int i = 1; i < names.size() - 1; i++) {
            if (prepositions.contains(names.get(i).toLowerCase(Locale.ROOT))) continue;
            cardName.append(" ")
                    .append(names.get(i).charAt(0));
        }

        cardName.append(" ")
                .append(names.get(names.size() - 1));

        return cardName.toString();
    }

    private Account checkAuthentication() {
        SpringSecurityAccount springSecurityAccount = AccountService.getAuthenticated();
        if (springSecurityAccount == null) {
            log.info("Attempt to make an operation without been authenticated.");
            throw new AuthorizationException("Forbidden");
        }
        return Account.builder()
                .id(springSecurityAccount.getId())
                .checkDigit(springSecurityAccount.getCheckDigit())
                .balance(springSecurityAccount.getBalance())
                .status(springSecurityAccount.getAccountStatus().getCode())
                .build();
    }

    private String encrypt(String data) {
        return new BCryptPasswordEncoder().encode(data);
    }

}

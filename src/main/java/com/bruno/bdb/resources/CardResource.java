package com.bruno.bdb.resources;

import com.bruno.bdb.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/cards")
@RequiredArgsConstructor
public class CardResource {

    private final CardService cardService;

    @PostMapping(value = "/activate")
    public ResponseEntity<Void> activate() {
        cardService.activate();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/confirmation")
    public ResponseEntity<Void> receiptConfirmation() {
        cardService.receiptConfirmation();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/send")
    public ResponseEntity<Void> sendCard(@RequestParam(value = "card") String cardId) {
        cardService.sendCard(cardId);
        return ResponseEntity.noContent().build();
    }
}

package com.bruno.bdb.resources;

import com.bruno.bdb.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/cards")
@RequiredArgsConstructor
public class CardResource {

    private final CardService cardService;

    @PatchMapping(value = "/activate")
    public ResponseEntity<Void> activate() {
        cardService.activate();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/confirmation")
    public ResponseEntity<Void> receiptConfirmation() {
        cardService.receiptConfirmation();
        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("hasAnyRole('MANAGER')")
    @PatchMapping(value = "/send")
    public ResponseEntity<Void> sendCard(@RequestParam(value = "card") Long cardId) {
        cardService.sendCard(cardId);
        return ResponseEntity.noContent().build();
    }
}

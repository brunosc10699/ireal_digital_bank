package com.bruno.bdb.resources;

import com.bruno.bdb.domain.Transaction;
import com.bruno.bdb.dto.PaymentDTO;
import com.bruno.bdb.dto.TransactionDTO;
import com.bruno.bdb.dto.TransferDTO;
import com.bruno.bdb.dto.WithdrawDTO;
import com.bruno.bdb.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionResource {

    private final TransactionService transactionService;

    @PostMapping(value = "/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferDTO transferDTO) {
        Transaction transfer = transactionService.transfer(transferDTO);
        log.info("Transfer done. Transaction id: '" + transfer.getId() + "'");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transfer.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/payment")
    public ResponseEntity<TransactionDTO> payment(@Valid @RequestBody PaymentDTO paymentDTO) {
        Transaction payment = transactionService.payment(paymentDTO);
        log.info("Payment done. Transaction id: '" + payment.getId() + "'");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.getId())
                .toUri();
        return ResponseEntity.created(uri).body(paymentDTO.fromEntity(payment));
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@Valid @RequestBody WithdrawDTO withdrawDTO) {
        Transaction withdraw = transactionService.withdraw(withdrawDTO);
        log.info("Withdraw done. Transaction id: '" + withdraw.getId() + "'");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(withdraw.getId())
                .toUri();
        return ResponseEntity.created(uri).body(withdrawDTO.fromEntity(withdraw));
    }

}

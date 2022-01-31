package com.bruno.bdb.services;

import com.bruno.bdb.domain.Transaction;
import com.bruno.bdb.dto.PaymentDTO;
import com.bruno.bdb.dto.TransferDTO;
import com.bruno.bdb.dto.WithdrawDTO;

public interface TransactionService {

    Transaction transfer(TransferDTO transferDTO);

    Transaction payment(PaymentDTO paymentDTO);

    Transaction withdraw(WithdrawDTO withdrawDTO);
}

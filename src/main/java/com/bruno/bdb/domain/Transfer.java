package com.bruno.bdb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_transfers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Transfer extends Transaction {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, updatable = false)
    private Integer destinationBranchNumber;

    @Column(nullable = false, updatable = false)
    private Long destinationAccountNumber;

    @Column(nullable = false, updatable = false)
    private Integer destinationAccountCheckDigit;

    @Column(nullable = false, updatable = false)
    private String destinationHolderCpf;

}

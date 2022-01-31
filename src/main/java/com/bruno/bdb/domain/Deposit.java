package com.bruno.bdb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_deposits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Deposit extends Transaction {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false, updatable = false)
    private String bankingSlipCode;

    @Column(nullable = false, length = 1)
    private Integer status;

}

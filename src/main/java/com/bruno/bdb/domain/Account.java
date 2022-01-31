package com.bruno.bdb.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number", updatable = false)
    private Long id;

    @Column(length = 1, updatable = false)
    private Integer checkDigit;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime openingDate;

    @Column(updatable = false)
    private LocalDateTime closureDate;

    @Column(nullable = false, length = 1)
    private Integer type;

    @Column(nullable = false, length = 1)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "holder_cpf")
    private Holder holder;

    @ManyToOne
    @JoinColumn(name = "branch_number")
    private Branch branch;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Card card;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

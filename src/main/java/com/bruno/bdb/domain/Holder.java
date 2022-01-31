package com.bruno.bdb.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_holders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Holder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cpf", nullable = false, length = 11)
    private String id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String surName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal income;

    @Column(nullable = false, updatable = false)
    private LocalDateTime signUpDate;

    @Column(nullable = false, length = 1)
    private Integer status;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private Set<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Holder user = (Holder) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

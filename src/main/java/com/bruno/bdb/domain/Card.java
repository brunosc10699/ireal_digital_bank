package com.bruno.bdb.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(nullable = false, length = 16, unique = true)
    private String number;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 5, updatable = false)
    private String expirationDate;

    @Column(nullable = false, length = 20, updatable = false)
    private String name;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false, length = 1)
    private Integer status;

    @OneToOne
    @JoinColumn(name = "account_number")
    @MapsId
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

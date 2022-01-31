package com.bruno.bdb.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_branches")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_number")
    private Integer id;

    @Column(nullable = false, length = 50, updatable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime openingDate;

    @Column(nullable = false, length = 1)
    private Integer status;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Branch branch = (Branch) o;
        return id != null && Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

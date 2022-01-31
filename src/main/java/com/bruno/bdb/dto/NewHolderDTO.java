package com.bruno.bdb.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
public class NewHolderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "Enter the first name")
    @Length(message = "First name characters are limited", min = 2, max = 30)
    private String firstName;

    @NotNull(message = "Enter the first name")
    @Length(message = "First name characters are limited", min = 2, max = 20)
    private String surName;

    private String email;

    private String accountPassword;

    private BigDecimal income;
}

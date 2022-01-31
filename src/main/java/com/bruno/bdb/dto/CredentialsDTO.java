package com.bruno.bdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredentialsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String password;
}
package com.bruno.bdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HolderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String surName;
}

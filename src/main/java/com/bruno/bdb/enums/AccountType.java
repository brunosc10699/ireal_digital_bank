package com.bruno.bdb.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AccountType {

    SAVINGS(0, "Savings");

    private final int code;
    private final String description;


    public static AccountType toEnum(Integer code) {
        if (code == null) return null;

        for (AccountType type : AccountType.values()) {
            if (code == type.getCode()) return type;
        }

        throw new IllegalArgumentException("Invalid status code: '" + code + "'");
    }

}

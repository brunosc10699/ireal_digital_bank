package com.bruno.bdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Role {

    ADMIN(0, "ROLE_ADMIN"),
    GENERAL_MANAGER(1, "ROLE_MANAGER"),
    MANAGER(2, "ROLE_USER"),
    CUSTOMER(3, "ROLE_CUSTOMER");

    private int code;
    private String description;

    public static Role toEnum(Integer code) {
        if (code == null) return null;
        for (Role profile : Role.values()) {
            if (code.equals(profile.getCode())) return profile;
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}

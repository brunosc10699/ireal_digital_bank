package com.bruno.bdb.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Status {

    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active"),
    BLOCKED(2, "Blocked");

    private final int code;
    private final String description;

    public static Status toEnum(Integer code) {
        if (code == null) return null;

        for (Status status : Status.values()) {
            if (code == status.getCode()) return status;
        }

        throw new IllegalArgumentException("Invalid status code: '" + code + "'");
    }

}

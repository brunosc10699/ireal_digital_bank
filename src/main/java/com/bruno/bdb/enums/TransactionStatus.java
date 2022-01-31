package com.bruno.bdb.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TransactionStatus {

    FAIL(0, "Fail"),
    SUCCESS(1, "Success"),
    PENDING(2, "Pending");

    private final int code;
    private final String description;


    public static TransactionStatus toEnum(Integer code) {
        if (code == null) return null;

        for (TransactionStatus status : TransactionStatus.values()) {
            if (code == status.getCode()) return status;
        }

        throw new IllegalArgumentException("Invalid status code: '" + code + "'");
    }

}

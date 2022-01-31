package com.bruno.bdb.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CardStatus {

    BUILT(0, "Built"),
    SENT(1, "Sent"),
    ACTIVE(2, "Active"),
    RECEIVED(3, "Received"),
    BLOCKED(4, "Blocked");

    private final int code;
    private final String description;

    public static CardStatus toEnum(Integer code) {
        if (code == null) return null;

        for (CardStatus status : CardStatus.values()) {
            if (code == status.getCode()) return status;
        }

        throw new IllegalArgumentException("Invalid status code: '" + code + "'");
    }

}

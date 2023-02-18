package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsLock {

    NOT_LOCK(0,"未锁定"),

    LOCK(1,"已锁定");


    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (IsLock value : IsLock.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }
}

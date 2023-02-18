package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsVal {

    NOT_VAL(0,"未生效"),

    VAL(1,"已生效"),

    REJECT(2,"已驳回");


    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (IsVal value : IsVal.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }
}

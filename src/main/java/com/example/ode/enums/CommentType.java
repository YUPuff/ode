package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentType {
    DISH(1,"菜品"),

    SERVICE(2,"服务"),

    ENVIRONMENT(3,"环境");


    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (CommentType value : CommentType.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }
}

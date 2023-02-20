package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: lyl
 * @Date: 2023-02-20 11:01
 **/

@Getter
@AllArgsConstructor
public enum CommentLevel {
    BAD(0,"差"),

    MEDIUM(1,"中"),

    GOOD(2,"好");


    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (CommentLevel value : CommentLevel.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }
}

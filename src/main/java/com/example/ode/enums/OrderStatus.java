package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    NOT_START(0,"未开始"),

    ING(1,"进行中"),

    WAIT_TO_COMMENT(2,"待评价"),

    FINISHED(3,"已完成"),

    CANCELED(4,"已取消");

    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }
}

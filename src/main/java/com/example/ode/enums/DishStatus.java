package com.example.ode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜品状态（0：未烹饪，1：烹饪中，2：待上菜，3：已完成，4：已取消）
 */
@Getter
@AllArgsConstructor
public enum DishStatus {

    WAIT_TO_COOK(0,"未烹饪"),

    COOKING(1,"烹饪中"),

    WAIT_TO_SERVE(2,"待上菜"),

    FINISHED(3,"已完成"),

    CANCELED(4,"已取消");

    private Integer code;

    private String status;

    public static String format(Integer code) {
        for (DishStatus value : DishStatus.values()) {
            if (value.code == code) {
                return value.status;
            }
        }
        return "";
    }

}

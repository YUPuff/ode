package com.example.ode.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description:
 * @Date: 2023-02-19 21:32
 **/

@Data
public class OrderDishVO {

    /**
     * 点菜id
     */
    private Long id;

    /**
     * 菜品id
     */
    private Long dishId;
    /**
     * 菜品名
     */
    private String name;

    /**
     * 桌号
     */
    private Long tableId;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片路径
     */
    private String pic;

    /**
     * 点菜数
     */
    private Integer amount;

    /**
     * 状态
     */
    private Integer status;
}

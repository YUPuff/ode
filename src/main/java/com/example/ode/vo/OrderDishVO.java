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
    private Integer id;
    /**
     * 菜品名
     */
    private String name;
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
}

package com.example.ode.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class DishVO {

    private Long id;
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
     * 菜品简介
     */
    private String intro;

    /**
     * 菜品详细介绍
     */
    private String detail;

    /**
     * 菜品评论数
     */
    private Map<String,Long> comments;
}

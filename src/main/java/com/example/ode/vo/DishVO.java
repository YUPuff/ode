package com.example.ode.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishVO {

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
     * 菜品食材
     */
    private String ingredient;


    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private String pic;
}

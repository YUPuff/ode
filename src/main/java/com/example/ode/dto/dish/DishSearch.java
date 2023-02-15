package com.example.ode.dto.dish;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2023-01-29 20:56
 **/

@Data
public class DishSearch {

    /**
     * 菜品名
     */
    private String name;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;


    /**
     * 所在分类代号
     */
    @ApiModelProperty(value = "所在分类代号")
    private Integer type;

    /**
     * 当前页码，用于分页
     */
    private Integer pageNum;

    /**
     * 当前页大小
     */
    private Integer pageSize;
}

package com.example.ode.dto.dish;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    private Integer type;

    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;

    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    private Integer pageSize;
}

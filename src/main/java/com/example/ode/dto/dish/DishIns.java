package com.example.ode.dto.dish;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: lyl
 * @Description: 菜品添加类
 * @Date: 2023-01-29 20:53
 **/

@Data
public class DishIns {


    /**
     * 菜品名
     */
    @NotBlank(message = "菜品名不能为空")
    @Length(message = "菜品名不能超过{max}个字符",max = 10)
    private String name;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0", message = "价格必须为正数")
    @Digits(integer = 7, fraction = 2, message=  "价格整数位不能超过7个,小数位不能超过2个")
    private BigDecimal price;

    /**
     * 简介
     */
    private String intro;

    /**
     * 菜品具体介绍
     */
    private String detail;

    /**
     * 所在分类代号
     */
    @NotNull(message = "所在分类不能为空")
    private Integer type;

    /**
     * 图片路径
     */
    private String pic;
}

package com.example.ode.dto.dish;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
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
    @ApiModelProperty(value = "菜品名")
    @NotBlank(message = "菜品名不能为空")
    @Length(message = "菜品名不能超过{max}个字符",max = 10)
    private String name;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    @NotBlank(message = "价格不能为空")
    @DecimalMin(value = "0", message = "价格必须为正数")
    @Digits(integer = 7, fraction = 2, message = "价格整数位不能超过7个,小数位不能超过2个")
    private BigDecimal price;

    /**
     * 辣度（0：不辣，1：微辣，：2：中辣，3：特辣）
     */
    @ApiModelProperty(value = "辣度（0：不辣，1：微辣，：2：中辣，3：特辣）")
    @NotBlank(message = "辣度不能为空")
    private Integer spicy;

    /**
     * 口味（0：淡，1：中等，2：重口）
     */
    @ApiModelProperty(value = "口味（0：淡，1：中等，2：重口）")
    @NotBlank(message = "口味不能为空")
    private Integer salt;

    /**
     * 份量（0：小，1：中等，2：大）
     */
    @ApiModelProperty(value = "份量（0：小，1：中等，2：大）")
    @NotBlank(message = "份量不能为空")
    private Integer size;

    /**
     * 菜品食材
     */
    @ApiModelProperty(value = "菜品食材")
    private String ingredient;
    /**
     * 菜品具体介绍
     */
    @ApiModelProperty(value = "菜品具体介绍")
    private String detail;
    /**
     * 所在分类代号
     */
    @ApiModelProperty(value = "所在分类代号")
    @NotBlank(message = "分类不能为空")
    private Integer type;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private String pic;
}

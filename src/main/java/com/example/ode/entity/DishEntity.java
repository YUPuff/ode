package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Data
@TableName("t_dish")
@ApiModel(value = "Dish对象")
public class DishEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜品Id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "菜品Id")
	private Long id;
	/**
	 * 菜品名
	 */
	@ApiModelProperty(value = "菜品名")
	private String name;
	/**
	 * 价格
	 */
	@ApiModelProperty(value = "价格")
	private BigDecimal price;

	/**
	 * 菜品食材
	 */
	@ApiModelProperty(value = "菜品食材")
	private String intro;


	/**
	 * 菜品具体介绍
	 */
	@ApiModelProperty(value = "菜品具体介绍")
	private String detail;
	/**
	 * 所在分类代号
	 */
	@ApiModelProperty(value = "所在分类代号")
	private Integer type;

	/**
	 * 图片路径
	 */
	@ApiModelProperty(value = "图片路径")
	private String pic;

	/**
	 * 是否删除（0：否，1：是）
	 */
	@ApiModelProperty(value = "是否删除（0：否，1：是）")
	@TableLogic
	private Integer isDel;

	/**
	 * 备用
	 */
	@ApiModelProperty(value = "备用")
	private String extra;


}

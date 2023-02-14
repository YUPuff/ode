package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Data
@TableName("t_dish")
@ApiModel(value = "Dish对象")
public class DishEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜品Id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "菜品Id")
	private Integer id;
	/**
	 * 菜品名
	 */
	@ApiModelProperty(value = "菜品名")
	private String name;
	/**
	 * 价格
	 */
	@ApiModelProperty(value = "价格")
	private Double price;
	/**
	 * 菜品关键词
	 */
	@ApiModelProperty(value = "菜品关键词")
	private String key;
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
	private Integer isDel;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date addTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updTime;
	/**
	 * 备用
	 */
	@ApiModelProperty(value = "备用")
	private String extra;

}

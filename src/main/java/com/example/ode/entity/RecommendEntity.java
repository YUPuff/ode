package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
@Data
@TableName("t_recommend")
@ApiModel(value = "Recommend对象")
public class RecommendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 菜品id
	 */
	@ApiModelProperty(value = "菜品id")
	private Long dishId;
	/**
	 * 次数
	 */
	@ApiModelProperty(value = "次数")
	private Integer count;
	/**
	 * 偏好值
	 */
	@ApiModelProperty(value = "偏好值")
	private Float preference;
	/**
	 * 添加时间
	 */
	@ApiModelProperty(value = "添加时间")
	@TableField(fill = FieldFill.INSERT)
	private Date addTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updTime;

}

package com.example.ode.entity;

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
@TableName("t_comment")
@ApiModel(value = "Comment对象")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 评价Id
	 */
	@TableId
	@ApiModelProperty(value = "评价Id")
	private Integer id;
	/**
	 * 评价类型（1：菜品评价，2：服务评价，3：环境评价）
	 */
	@ApiModelProperty(value = "评价类型（1：菜品评价，2：服务评价，3：环境评价）")
	private Integer type;
	/**
	 * 目标id（如果此条评价是菜品评价的话，此列值不为空）
	 */
	@ApiModelProperty(value = "目标id（如果此条评价是菜品评价的话，此列值不为空）")
	private Integer target;
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private Integer orderId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 等级（0：差，1：中，2：好）
	 */
	@ApiModelProperty(value = "等级（0：差，1：中，2：好）")
	private Integer level;
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
	 * 备用
	 */
	@ApiModelProperty(value = "备用")
	private String extra;

}
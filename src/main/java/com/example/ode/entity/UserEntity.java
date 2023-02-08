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
 * @date 2023-02-08 17:35:28
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId
	@ApiModelProperty(value = "用户Id")
	private Integer id;
	/**
	 * 用户唯一标识
	 */
	@ApiModelProperty(value = "用户唯一标识")
	private String openId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String name;
	/**
	 * 省份
	 */
	@ApiModelProperty(value = "省份")
	private String province;
	/**
	 * 城市
	 */
	@ApiModelProperty(value = "城市")
	private String city;
	/**
	 * 头像路径
	 */
	@ApiModelProperty(value = "头像路径")
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
	 * 备用
	 */
	@ApiModelProperty(value = "备用")
	private String extra;

}

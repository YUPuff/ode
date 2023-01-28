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
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String name;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 角色代号（0：普通用户，1：管理员）
	 */
	@ApiModelProperty(value = "角色代号（0：普通用户，1：管理员）")
	private Integer role;
	/**
	 * 是否锁定（0：否，1：是）
	 */
	@ApiModelProperty(value = "是否锁定（0：否，1：是）")
	private Integer isLock;
	/**
	 * 是否删除（0：否，1：是）
	 */
	@ApiModelProperty(value = "是否删除（0：否，1：是）")
	private Integer isDel;
	/**
	 * 头像路径
	 */
	@ApiModelProperty(value = "头像路径")
	private String pic;
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

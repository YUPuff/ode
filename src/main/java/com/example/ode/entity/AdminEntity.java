package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("t_admin")
@ApiModel(value = "Admin对象")
public class AdminEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "用户Id")
	private Long id;
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
	 * 角色（0：管理员，1：服务员，2：后厨）
	 */
	private Integer role;
	/**
	 * 是否删除（0：否，1：是）
	 */
	@TableLogic
	private Integer isDel;

	/**
	 * 是否生效（0：否，1：是，2：已驳回）
	 */
	private Integer isVal;

	/**
	 * 是否锁定（0：否，1：是）
	 */
	private Integer isLock;
	/**
	 * 头像路径
	 */
	private String pic;


}

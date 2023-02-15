package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.example.ode.common.WxUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yilin
 * @date 2023-02-08 17:35:28
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "用户Id")
	private Long id;
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
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String gender;

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
	@TableLogic
	private Integer isDel;


	public UserEntity(WxUserInfo userInfo){
		this.openId = userInfo.getOpenId();
		this.name = userInfo.getNickName();
		this.pic = userInfo.getAvatarUrl();
		this.province = userInfo.getProvince();
		this.city = userInfo.getCity();
		this.gender = userInfo.getGender();
	}

}

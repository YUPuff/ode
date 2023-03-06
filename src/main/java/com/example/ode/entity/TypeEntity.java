package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("t_type")
@ApiModel(value = "Type对象")
public class TypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "id")
	private Integer id;
	/**
	 * 分类代号
	 */
	@ApiModelProperty(value = "分类代号")
	private Integer number;
	/**
	 * 分类名
	 */
	@ApiModelProperty(value = "分类名")
	private String name;

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

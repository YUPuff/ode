package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yilin
 * @date 2023-02-24 15:08:34
 */
@Data
@TableName("t_detail")
@ApiModel(value = "Detail对象")
public class DetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Long orderId;

	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Long dishId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String dishName;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Integer count;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private BigDecimal price;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Date addTime;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Long userId;

}

package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Data
@TableName("t_order")
@ApiModel(value = "Order对象")
public class OrderEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单Id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "订单Id")
	private Long id;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;

	/**
	 * 桌号
	 */
	@ApiModelProperty(value = "桌号")
	private Integer tableId;

	/**
	 * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
	 */
	@ApiModelProperty(value = "（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）")
	private Integer status;

	/**
	 * 总金额
	 */
	@ApiModelProperty(value = "总金额")
	private BigDecimal total;

	/**
	 * 订单备注
	 */
	@ApiModelProperty(value = "订单备注")
	private String remark;

	/**
	 * 就餐人数
	 */
	@ApiModelProperty(value = "就餐人数")
	private Integer people;

}

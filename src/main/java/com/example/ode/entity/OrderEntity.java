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
@TableName("t_order")
@ApiModel(value = "Order对象")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单Id
	 */
	@TableId
	@ApiModelProperty(value = "订单Id")
	private Integer id;
	/**
	 * 桌号
	 */
	@ApiModelProperty(value = "桌号")
	private Integer tableId;
	/**
	 * 订单状态（0：进行中，1：待评价，2：已完成，3：已取消）
	 */
	@ApiModelProperty(value = "订单状态（0：进行中，1：待评价，2：已完成，3：已取消）")
	private Integer state;
	/**
	 * 总金额
	 */
	@ApiModelProperty(value = "总金额")
	private Double total;
	/**
	 * 菜品关键词
	 */
	@ApiModelProperty(value = "菜品关键词")
	private String key;
	/**
	 * 订单备注
	 */
	@ApiModelProperty(value = "订单备注")
	private String remark;
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

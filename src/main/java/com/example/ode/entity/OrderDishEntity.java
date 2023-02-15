package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_order_dish")
@ApiModel(value = "OrderDish对象")
public class OrderDishEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "id")
	private Long id;
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private Long orderId;
	/**
	 * 菜品id
	 */
	@ApiModelProperty(value = "菜品id")
	private Integer dishId;
	/**
	 * 菜品状态（0：未烹饪，1：烹饪中，2：待上菜，3：已完成，4：已取消）
	 */
	@ApiModelProperty(value = "菜品状态（0：未烹饪，1：烹饪中，2：待上菜，3：已完成，4：已取消）")
	private Integer state;
	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量")
	private Integer amount;


}

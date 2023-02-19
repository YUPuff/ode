package com.example.ode.dto.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: lyl
 * @Date: 2023-02-19 21:18
 **/

@Data
public class OrderDTO {

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    private Long id;

    /**
     * 当前页码
     */
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;

}

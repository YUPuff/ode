package com.example.ode.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSearch {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 桌号
     */
    private Integer tableId;

    /**
     * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
     */
    private Integer status;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;
}

package com.example.ode.dto.order;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderSearch {


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 桌号
     */
    private Integer tableId;

    /**
     * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
     */
    @Max(value = 4,message = "订单状态只能是0~4")
    @Min(value = 0,message = "订单状态只能是0~4")
    private Integer status;

    /**
     * 最低金额
     */
    private BigDecimal minTotal;

    /**
     * 最高金额
     */
    private BigDecimal maxTotal;

    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;

    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    private Integer pageSize;
}

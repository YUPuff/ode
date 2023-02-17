package com.example.ode.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    private Integer pageNum;

    /**
     * 当前页大小
     */
    private Integer pageSize;
}

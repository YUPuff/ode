package com.example.ode.dto.order;

import com.example.ode.dto.dish.DishDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderIns {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 桌号
     */
    @NotNull(message = "桌号不能为空")
    private Integer tableId;

    /**
     * 菜品集合
     */
    @NotEmpty(message = "未选择任何菜品")
    private List<DishDTO> dishes;

    /**
     * 订单备注
     */
    private String remark;


    private BigDecimal total;

}

package com.example.ode.dto.order;

import com.example.ode.dto.dish.DishDTO;
import lombok.Data;

import javax.validation.constraints.Digits;
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
     * 人数
     */
    @NotNull(message = "人数不能为空")
    private Integer people;

    /**
     * 菜品集合
     */
    @NotEmpty(message = "未选择任何菜品")
    private List<DishDTO> dishes;

    /**
     * 订单备注
     */
    private String remark;


    @NotNull(message = "总金额不能为空")
    @Digits(integer = 7, fraction = 2, message=  "总金额整数位不能超过7个,小数位不能超过2个")
    private BigDecimal total;

}

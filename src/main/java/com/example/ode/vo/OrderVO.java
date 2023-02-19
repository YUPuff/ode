package com.example.ode.vo;

import com.example.ode.common.MyPage;
import com.example.ode.entity.OrderEntity;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2023-02-19 20:38
 **/

@Data
public class OrderVO extends OrderEntity {

    /**
     * 订单包含的菜品
     */
    private MyPage<OrderDishVO> dishes;
}

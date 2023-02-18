package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.order.OrderIns;
import com.example.ode.dto.order.OrderSearch;
import com.example.ode.entity.OrderEntity;

import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface OrderService extends IService<OrderEntity> {

    void add(OrderIns ins);

    void updateStatus(Long id);

    void cancelOrder(Long id);

    MyPage<OrderEntity> getOrders(OrderSearch search);

}


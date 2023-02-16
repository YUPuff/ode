package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ode.common.BusinessException;
import com.example.ode.constant.ResultConstant;
import com.example.ode.entity.OrderEntity;
import com.example.ode.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.OrderDishDao;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.OrderDishService;


@Service("orderDishService")
public class OrderDishServiceImpl extends ServiceImpl<OrderDishDao, OrderDishEntity> implements OrderDishService {

    @Autowired
    private OrderDishDao orderDishDao;

    @Autowired
    private OrderService orderService;

    /**
     * 菜品状态（0：未烹饪，1：烹饪中，2：待上菜，3：已完成，4：已取消）
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        OrderDishEntity entity = orderDishDao.selectById(id);
        if (entity == null) throw new BusinessException(ResultConstant.ORDER_DISH_NO_EXIST_EXCEPTION);
        int status = entity.getStatus();
        if (status == 3 || status == 4) throw new BusinessException(ResultConstant.ORDER_DISH_CANT_EXCEPTION);
        entity.setStatus(status+1);
        orderDishDao.updateById(entity);
        // 如果订单第一道菜开始烹饪、最后一道菜完成烹饪则分别修改订单状态
        OrderEntity order = orderService.getOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getId, entity.getOrderId()));
        if (order.getStatus() == 0) orderService.updateStatus(order.getId());
        else if (status == 2){
            // 查询当前订单是否还有未完成的菜品
            OrderDishEntity orderDishEntity = orderDishDao.selectOne(new LambdaQueryWrapper<OrderDishEntity>()
                    .lt(OrderDishEntity::getStatus, 3)
                    .eq(OrderDishEntity::getOrderId, entity.getOrderId()));
            if (orderDishEntity == null) orderService.updateStatus(order.getId());
        }
    }

    @Override
    public void cancelDish(Long id) {
        OrderDishEntity entity = orderDishDao.selectById(id);
        if (entity == null) throw new BusinessException(ResultConstant.ORDER_DISH_NO_EXIST_EXCEPTION);
        int status = entity.getStatus();
        if (status != 0) throw new BusinessException(ResultConstant.ORDER_DISH_CANT_EXCEPTION);
        entity.setStatus(4);
        orderDishDao.updateById(entity);
    }
}
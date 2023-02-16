package com.example.ode.service.impl;

import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.dish.DishDTO;
import com.example.ode.dto.order.OrderIns;
import com.example.ode.dto.order.OrderSearch;
import com.example.ode.entity.DishEntity;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.OrderDao;
import com.example.ode.entity.OrderEntity;
import com.example.ode.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderDishService orderDishService;

    /**
     * 添加新订单
     * @param ins
     */
    @Override
    @Transactional
    public void add(OrderIns ins) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableId(ins.getTableId());
        orderEntity.setRemark(ins.getRemark());
        // 先插入订单表以获取生成的订单id
        orderDao.insert(orderEntity);
        Long orderId = orderEntity.getId();
        // 遍历菜品集合
        List<DishDTO> dishes = ins.getDishes();
        BigDecimal total = new BigDecimal(0);
        for(DishDTO dish:dishes){
            // 将订单菜品关系插入对应表中
            OrderDishEntity orderDishEntity = new OrderDishEntity();
            orderDishEntity.setOrderId(orderId);
            orderDishEntity.setDishId(dish.getId());
            orderDishEntity.setAmount(dish.getAmount());
            orderDishService.save(orderDishEntity);
            // 计算出总金额
            DishEntity dishEntity = dishService.getOneDish(dish.getId());
            total.add(dishEntity.getPrice().multiply(new BigDecimal(dish.getAmount())));
        }
        // 修改订单其他相关信息
        orderEntity.setTotal(total);
        orderDao.updateById(orderEntity);
    }

    /**
     * 修改订单状态（开始烹饪第一道菜、烹饪完最后一道菜、用户评价结束）
     * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        OrderEntity order = orderDao.selectById(id);
        if (order == null) throw new BusinessException(ResultConstant.ORDER_NO_EXIST_EXCEPTION);
        int status = order.getStatus();
        if (status == 3 || status == 4) throw new BusinessException(ResultConstant.ORDER_CANT_EXCEPTION);
        order.setStatus(status+1);
        orderDao.updateById(order);
    }

    /**
     * 只有当订单状态未开始时才能被用户取消
     * @param id
     */
    @Override
    public void cancelOrder(Long id) {
        OrderEntity order = orderDao.selectById(id);
        if (order == null) throw new BusinessException(ResultConstant.ORDER_NO_EXIST_EXCEPTION);
        int status = order.getStatus();
        if (status != 0) throw new BusinessException(ResultConstant.ORDER_CANT_EXCEPTION);
        order.setStatus(4);
        orderDao.updateById(order);
    }

    @Override
    public MyPage<OrderEntity> getOrders(OrderSearch search) {
        return null;
    }

}
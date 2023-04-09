package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.vo.DishVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface OrderDishService extends IService<OrderDishEntity> {

    void updateStatus(Long id);

    void cancelDish(Long id);

    List<DishVO> getTop5Dishes();

    Map<String,List> getToDo();
}


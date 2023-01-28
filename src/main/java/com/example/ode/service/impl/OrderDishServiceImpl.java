package com.example.ode.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.OrderDishDao;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.OrderDishService;


@Service("orderDishService")
public class OrderDishServiceImpl extends ServiceImpl<OrderDishDao, OrderDishEntity> implements OrderDishService {


}
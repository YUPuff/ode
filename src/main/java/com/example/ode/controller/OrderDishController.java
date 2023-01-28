package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.OrderDishService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("orderDish")
public class OrderDishController {
    @Autowired
    private OrderDishService orderDishService;


}

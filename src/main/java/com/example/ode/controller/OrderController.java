package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import com.example.ode.common.Result;
import com.example.ode.dto.order.OrderIns;
import com.example.ode.dto.order.OrderSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.entity.OrderEntity;
import com.example.ode.service.OrderService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/add")
    public Result add(@Validated OrderIns ins){
        orderService.add(ins);
        return Result.success();
    }

    @GetMapping("/upd/{id}")
    public Result updateStatus(@PathVariable("id")Long id){
        orderService.updateStatus(id);
        return Result.success();
    }

    @GetMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id")Long id){
        orderService.cancelOrder(id);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id")Long id){
        return Result.success(orderService.detail(id));
    }

    @PostMapping("/get")
    public Result getOrders(OrderSearch search){
        return Result.success(orderService.getOrders(search));
    }
}

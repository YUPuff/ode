package com.example.ode.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.Result;
import com.example.ode.dto.order.OrderDTO;
import com.example.ode.dto.order.OrderIns;
import com.example.ode.dto.order.OrderSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.example.ode.entity.OrderEntity;
import com.example.ode.service.OrderService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Result add(@Validated @RequestBody OrderIns ins){
        orderService.add(ins);
        return Result.success();
    }

    @RequestMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id")Long id){
        orderService.cancelOrder(id);
        return Result.success();
    }

    @RequestMapping("/detail")
    public Result detail(@Validated OrderDTO orderDTO){
        return Result.success(orderService.detail(orderDTO));
    }

    @RequestMapping("/get")
    public Result getOrders(@Validated OrderSearch search){
        return Result.success(orderService.getOrders(search));
    }

}

package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import com.example.ode.common.Result;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.OrderDishService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("orderDish")
@CrossOrigin
public class OrderDishController {
    @Autowired
    private OrderDishService orderDishService;

    @RequestMapping("/upd/{id}")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public Result updateStatus(@PathVariable("id") Long id){
        orderDishService.updateStatus(id);
        return Result.success();
    }

    @RequestMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id")Long id){
        orderDishService.cancelDish(id);
        return Result.success();
    }

    @RequestMapping("/getTop5Dishes")
    public Result getTop5Dishes(){
        return Result.success(orderDishService.getTop5Dishes());
    }

    @GetMapping("/getToDo")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public Result getToDo(){
        return Result.success(orderDishService.getToDo());
    }

}

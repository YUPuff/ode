package com.example.ode.controller;



import com.example.ode.common.Result;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.service.DishService;

import java.util.List;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @RequestMapping("/add")
    public Result add(@Validated @RequestBody DishIns ins){
        dishService.add(ins);
        return Result.success();
    }

    @RequestMapping("/upd")
    public Result update(@Validated @RequestBody DishUpd upd){
        dishService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    public Result delete(List<Integer> ids){
        dishService.delete(ids);
        return Result.success();
    }

    @RequestMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Integer id){
        return Result.success(dishService.getById(id));
    }

    @RequestMapping("/get")
    public Result getDishes(@Validated @RequestBody DishSearch search){
        return Result.success(dishService.getDishes(search));
    }
}

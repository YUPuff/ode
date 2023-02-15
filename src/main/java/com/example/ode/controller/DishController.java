package com.example.ode.controller;



import com.example.ode.common.Result;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public Result add(@Validated DishIns ins){
        dishService.add(ins);
        return Result.success();
    }

    @RequestMapping("/upd")
    public Result update(@Validated DishUpd upd){
        dishService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    public Result delete(List<Integer> ids){
        dishService.delete(ids);
        return Result.success();
    }

    @RequestMapping("/detail")
    public Result detail(Integer id){
        return Result.success(dishService.getOneDish(id));
    }

    @RequestMapping("/get")
    public Result getDishes(DishSearch search){
        return Result.success(dishService.getDishes(search));
    }
}

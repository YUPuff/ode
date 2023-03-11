package com.example.ode.controller;



import com.example.ode.common.Result;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public Result add(@Validated @RequestBody DishIns ins){
        dishService.add(ins);
        return Result.success();
    }

    @PostMapping("/upd")
    public Result update(@Validated @RequestBody DishUpd upd){
        dishService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    public Result delete(@RequestBody List<Long> ids){

        dishService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Long id){
        return Result.success(dishService.getById(id));
    }

    @RequestMapping("/get")
    public Result getDishes(@Validated @RequestBody DishSearch search){
        return Result.success(dishService.getDishes(search));
    }
}

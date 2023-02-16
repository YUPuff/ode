package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import com.example.ode.common.Result;
import com.example.ode.dto.type.TypeIns;
import com.example.ode.dto.type.TypeSearch;
import com.example.ode.dto.type.TypeUpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.entity.TypeEntity;
import com.example.ode.service.TypeService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("/add")
    public Result add(TypeIns ins){
        typeService.add(ins);
        return Result.success();
    }

    @RequestMapping("/upd")
    public Result update(TypeUpd upd){
        typeService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del/{id}")
    public Result delete(@PathVariable("id") Integer id){
        typeService.delete(id);
        return Result.success();
    }

    @RequestMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Integer id){
        return Result.success(typeService.getOneType(id));
    }

    @RequestMapping("/get")
    public Result get(TypeSearch search){
        return Result.success(typeService.getTypes(search));
    }
}

package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.type.TypeIns;
import com.example.ode.dto.type.TypeSearch;
import com.example.ode.dto.type.TypeUpd;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.TypeService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("type")
@CrossOrigin
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping("/add")
    @RequiresRoles("ADMIN")
    public Result add(@Validated @RequestBody TypeIns ins){
        typeService.add(ins);
        return Result.success();
    }

    @PostMapping("/upd")
    @RequiresRoles("ADMIN")
    public Result update(@Validated @RequestBody TypeUpd upd){
        typeService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del/{id}")
    @RequiresRoles("ADMIN")
    public Result delete(@PathVariable("id") Integer id){
        typeService.delete(id);
        return Result.success();
    }

    @RequestMapping("/detail/{id}")
    @RequiresRoles("ADMIN")
    public Result detail(@PathVariable("id") Integer id){
        return Result.success(typeService.getById(id));
    }

    @RequestMapping("/get")
    public Result get(@Validated TypeSearch search){
        return Result.success(typeService.getTypes(search));
    }
}

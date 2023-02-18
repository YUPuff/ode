package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.model.WXAuth;
import com.example.ode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login1")
    public Result login1(String code){
        return Result.success(userService.login1(code));
    }

    @PostMapping("/login2")
    public Result login2(WXAuth wxAuth){
        return Result.success(userService.login2(wxAuth));
    }


    @RequestMapping("/del")
    public Result del(List<Long> ids){
        return Result.success(userService.delete(ids));
    }

    @RequestMapping("/get")
    public Result getUser(@Validated @RequestBody UserSearch search){
        return Result.success(userService.getUser(search));
    }


}

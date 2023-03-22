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
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;



    @RequestMapping("/login")
    public Result login(@RequestBody WXAuth wxAuth){
        return Result.success(userService.login(wxAuth));
    }


    @RequestMapping("/del")
    public Result del(@RequestBody List<Long> ids){
        userService.delete(ids);
        return Result.success();
    }

    @RequestMapping("/get")
    public Result getUser(@Validated @RequestBody UserSearch search){
        return Result.success(userService.getUser(search));
    }

    @RequestMapping("/logout")
    public Result logout(){
        return null;
    }

}

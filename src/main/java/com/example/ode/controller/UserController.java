package com.example.ode.controller;


import com.example.ode.annotation.NoAuth;
import com.example.ode.common.Result;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.dto.user.UserUpd;
import com.example.ode.entity.UserEntity;
import com.example.ode.model.WXAuth;
import com.example.ode.service.AdminService;
import com.example.ode.service.UserService;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    @NoAuth
    public Result login(@RequestBody WXAuth wxAuth){
        return Result.success(userService.login(wxAuth));
    }


    @PostMapping("/upd")
    public Result update(@Validated @RequestBody UserUpd upd){
        userService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Long> ids){
        userService.delete(ids);
        return Result.success();
    }

    @RequestMapping("/get")
    public Result getUser(@Validated UserSearch search){
        return Result.success(userService.getUser(search));
    }

    @RequestMapping("/logout")
    public Result logout(@RequestHeader("token")String token){
        adminService.logout(token);
        return Result.success();
    }

}

package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import com.example.ode.common.Result;
import com.example.ode.dto.user.UserIns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ode.entity.UserEntity;
import com.example.ode.service.UserService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 借助微信平台登录，第一次登录默认注册为普通用户
     * @param map
     * @return
     */
    @PostMapping("/login")
    public Result login(UserIns ins){
        try{
            return Result.success(userService.login(ins));
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }


}

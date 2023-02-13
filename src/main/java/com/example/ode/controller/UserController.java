package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.user.UserIns;
import com.example.ode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(UserIns ins){
        try{
            return Result.success(userService.login(ins));
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }

    @RequestMapping("/test")
    public Result test(String code){
        return Result.success(userService.login1(code));
    }


}

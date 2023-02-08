package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.admin.AdminIns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.AdminService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private AdminService adminService;

    /**
     * 借助微信平台登录，第一次登录默认注册为普通用户
     * @param ins
     * @return
     */
    @PostMapping("/login")
    public Result login(AdminIns ins){
        try{
            return Result.success(adminService.login(ins));
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }

    @RequestMapping("/test")
    public Result test(String code){
        return Result.success(adminService.login(code));
    }


}

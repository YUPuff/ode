package com.example.ode.controller;

import java.util.List;


import com.example.ode.annotation.NoAuth;
import com.example.ode.common.Result;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.AdminService;


/**
 * @author yilin
 * @date 2023-02-08 17:35:28
 */
@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    @NoAuth
    public Result signup(@Validated @RequestBody AdminIns ins){
        adminService.signup(ins);
        return Result.success();
    }

    @PostMapping("/login")
    @NoAuth
    public Result login(@Validated @RequestBody AdminIns ins){
        return Result.success(adminService.login(ins));
    }

    @GetMapping("/info")
    @NoAuth
    public Result getInfo(@RequestParam String token){
        return Result.success(adminService.getAdminByToken(token));
    }


    @PostMapping("/upd")
    @RequiresRoles(value = {"ADMIN"})
    public Result update(@Validated @RequestBody AdminUpd upd){
        adminService.update(upd);
        return Result.success();
    }

    @PostMapping("/del")
    @RequiresRoles(value = {"ADMIN"})
    public Result delete(@RequestBody List<Integer> ids){
        adminService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public Result detail(@PathVariable("id") Long id){
        return Result.success(adminService.getOneAdmin(id));
    }

    @RequestMapping("/get")
    @RequiresRoles("ADMIN")
    public Result getAdmins(@Validated AdminSearch search){
        return Result.success(adminService.getAdmins(search));
    }

    @RequestMapping("/logout")
    public Result logout(@RequestHeader("token")String token){
        adminService.logout(token);
        return Result.success();
    }

    @GetMapping("/statistics")
    @RequiresRoles("ADMIN")
    public Result getStatistics(){
        return Result.success(adminService.getStatistics()).addOtherData("12Data",adminService.get12Data());
    }

    @GetMapping("/index/{id}")
    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public Result getDays(@PathVariable("id")Long id){
        return Result.success(adminService.index(id));
    }
}

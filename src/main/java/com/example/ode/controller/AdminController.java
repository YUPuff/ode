package com.example.ode.controller;

import java.util.List;


import com.example.ode.annotation.NoAuth;
import com.example.ode.common.Result;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
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


    @PostMapping("/upd")
    public Result update(@Validated @RequestBody AdminUpd upd){
        adminService.update(upd);
        return Result.success();
    }

    @GetMapping("/del")
    public Result delete(List<Integer> ids){
        adminService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Integer id){
        return Result.success(adminService.getOneAdmin(id));
    }

    @RequestMapping("/get")
    public Result getAdmins(@Validated @RequestBody AdminSearch search){
        return Result.success(adminService.getAdmins(search));
    }


}

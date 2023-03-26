package com.example.ode.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


import com.example.ode.annotation.NoAuth;
import com.example.ode.common.Result;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.AdminService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;


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
    public Result getInfo(@RequestParam String token){
        return Result.success(adminService.getAdminByToken(token));
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
    public Result getAdmins(@Validated AdminSearch search){
        return Result.success(adminService.getAdmins(search));
    }

    @RequestMapping("/logout")
    public Result logout(){
        return null;
    }

    @RequestMapping("/upload")
    public String uploadFile(MultipartFile photo) throws IOException {
        System.out.println("开始上传");
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        System.out.println("上传图片"+fileName);
        //处理文件重名问题
        String firstName = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = firstName+"-"+ UUID.randomUUID() + suffix;
        System.out.println("filename-------"+fileName);
        //获取服务器中photo目录的路径
//        ServletContext servletContext = session.getServletContext();
//        String photoPath = servletContext.getRealPath("photo");
        String photoPath = "/www/wwwroot/img";
        System.out.println(photoPath);
        File file = new File(photoPath);
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        System.out.println(finalPath);
        //实现上传功能
        photo.transferTo(new File(finalPath));

        return "img/"+fileName;
    }

}

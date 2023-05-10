package com.example.ode.controller;



import com.example.ode.annotation.NoAuth;
import com.example.ode.common.Result;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.DishService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("dish")
@CrossOrigin
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping("/add")
    @RequiresRoles("ADMIN")
    public Result add(@Validated @RequestBody DishIns ins){
        dishService.add(ins);
        return Result.success();
    }

    @PostMapping("/upd")
    @RequiresRoles("ADMIN")
    public Result update(@Validated @RequestBody DishUpd upd){
        dishService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    @RequiresRoles("ADMIN")
    public Result delete(@RequestBody List<Long> ids){
        dishService.delete(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Long id){
        return Result.success(dishService.getOneDish(id));
    }

    @RequestMapping("/get")
    public Result getDishes(@Validated DishSearch search){
        return Result.success(dishService.getDishes(search));
    }


    @RequestMapping("/upload")
    @NoAuth
    public Result uploadFile(MultipartFile file) throws IOException {
        //获取上传的文件的文件名
        String fileName = file.getOriginalFilename();
        //处理文件重名问题
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = prefix+"-"+ UUID.randomUUID() + suffix;
        //获取服务器中photo目录的路径
//        ServletContext servletContext = session.getServletContext();
//        String photoPath = servletContext.getRealPath("photo");
        String realPath = "/usr/project/photo/";
        String reflectPath = "https://yilin.site:8081/img/";
        File newFile = new File(realPath);
        // 判断路径下目录是否存在，不存在则创建
        if(!newFile.exists()){
            newFile.mkdir();
        }
        String filePath = realPath + fileName;
        String visitPath = reflectPath + fileName;
        //实现上传功能
        file.transferTo(new File(filePath));
        return Result.success(visitPath);
    }
}

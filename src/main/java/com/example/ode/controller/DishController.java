package com.example.ode.controller;



import com.example.ode.common.Result;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
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
    public Result add(@Validated @RequestBody DishIns ins){
        dishService.add(ins);
        return Result.success();
    }

    @PostMapping("/upd")
    public Result update(@Validated @RequestBody DishUpd upd){
        dishService.update(upd);
        return Result.success();
    }

    @RequestMapping("/del")
    public Result delete(@RequestBody List<Long> ids){

        dishService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id") Long id){
        return Result.success(dishService.getById(id));
    }

    @RequestMapping("/get")
    public Result getDishes(@Validated @RequestBody DishSearch search){
        return Result.success(dishService.getDishes(search));
    }


    @RequestMapping("/upload")
    public String uploadFile(MultipartFile photo, HttpServletRequest request) throws IOException {
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        //处理文件重名问题
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = prefix+"-"+ UUID.randomUUID() + suffix;
        //获取服务器中photo目录的路径
//        ServletContext servletContext = session.getServletContext();
//        String photoPath = servletContext.getRealPath("photo");
        String path = request.getServletContext().getRealPath("/photo/");
        File file = new File(path);
        // 判断路径下目录是否存在，不存在则创建
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = path + fileName;
        //实现上传功能
        photo.transferTo(new File(finalPath));
        return finalPath;
    }
}

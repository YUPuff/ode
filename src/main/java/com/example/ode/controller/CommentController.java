package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.CommentService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result add(@Validated @RequestBody CommentIns ins){
        commentService.add(ins);
        return Result.success();
    }

    @RequestMapping("/del/{id}")
    public Result delete(@PathVariable("id") Long id){
        commentService.removeById(id);
        return Result.success();
    }

    @RequestMapping("/get")
    public Result getComments(@Validated CommentSearch search){
        return Result.success(commentService.getComments(search));
    }

    @GetMapping("/dish/{id}")
    public Result getDishComment(@PathVariable("id")Long id){
        return Result.success(commentService.getCommentCount(id));
    }

    @GetMapping("entire")
    public Result getComment(Integer pageNum,Integer pageSize){
        return Result.success(commentService.getSEComment())
                .addOtherData("commentDetail",commentService.getCommentDetail(pageNum,pageSize));
    }

}

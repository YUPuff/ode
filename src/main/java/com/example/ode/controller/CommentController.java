package com.example.ode.controller;


import com.example.ode.common.Result;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.service.CommentService;



/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/add")
    public Result add(@Validated @RequestBody CommentIns ins){
        commentService.add(ins);
        return Result.success();
    }

    @RequestMapping("/del/{id}")
    public Result delete(@PathVariable("id") Long id){
        commentService.delete(id);
        return Result.success();
    }

    @RequestMapping("/get")
    public Result getComments(@RequestBody CommentSearch search){
        return Result.success(commentService.getComments(search));
    }

}

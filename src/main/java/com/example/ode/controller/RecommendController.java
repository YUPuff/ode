package com.example.ode.controller;



import com.example.ode.common.Result;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.service.RecommendService;



/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
@RestController
@RequestMapping("recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @GetMapping("/{id}")
    public Result ucn(@PathVariable("id")Long id) throws Exception {
        return Result.success(recommendService.recommend(id));
    }

}

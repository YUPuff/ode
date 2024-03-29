package com.example.ode.controller;



import com.example.ode.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ode.service.RecommendService;



/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
@RestController
@RequestMapping("recommend")
@CrossOrigin
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @GetMapping("/{id}")
    public Result recommend(@PathVariable("id")Long id) throws Exception {
        return Result.success(recommendService.recommend(id));
    }

    @GetMapping("/records/{id}")
    public Result getRecords(@PathVariable("id")Long id){
        return Result.success(recommendService.getRecordsForUser(id));
    }

}

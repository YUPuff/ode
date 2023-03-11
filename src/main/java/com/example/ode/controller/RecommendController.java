package com.example.ode.controller;



import com.example.ode.common.Result;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.entity.RecommendEntity;
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

    @GetMapping("/ucn/{id}/{num}")
    public Result ucn(@PathVariable("id")Long id,@PathVariable("num")Integer num) throws Exception {
        return Result.success(recommendService.user_cityBlock_nearestN(id,num));
    }

    @GetMapping("/uct/{id}/{threshold}")
    public Result uct(@PathVariable("id")Long id,@PathVariable("threshold")double threshold) throws Exception {
        return Result.success(recommendService.user_cityBlock_threshold(id,threshold));
    }

    @GetMapping("/uln/{id}/{num}")
    public Result uln(@PathVariable("id")Long id,@PathVariable("num")Integer num) throws Exception {
        return Result.success(recommendService.user_logLikelihood_nearestN(id,num));
    }
    @GetMapping("/ult/{id}/{threshold}")
    public Result ult(@PathVariable("id")Long id,@PathVariable("threshold")double threshold) throws Exception {
        return Result.success(recommendService.user_logLikelihood_threshold(id,threshold));
    }
    @GetMapping("/dc/{id}")
    public Result ic(@PathVariable("id")Long id) throws Exception {
        return Result.success(recommendService.dish_cityBlock(id));
    }

    @GetMapping("/dl/{id}")
    public Result il(@PathVariable("id")Long id) throws Exception {
        return Result.success(recommendService.dish_logLikelihood(id));
    }
}

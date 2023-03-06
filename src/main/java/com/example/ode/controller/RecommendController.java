package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.entity.RecommendEntity;
import com.example.ode.service.RecommendService;



/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
@RestController
@RequestMapping("Recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;


}

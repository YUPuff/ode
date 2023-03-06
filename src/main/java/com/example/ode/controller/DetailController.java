package com.example.ode.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ode.entity.DetailEntity;
import com.example.ode.service.DetailService;



/**
 * @author yilin
 * @date 2023-02-24 15:08:34
 */
@RestController
@RequestMapping("Detail")
public class DetailController {
    @Autowired
    private DetailService detailService;


}

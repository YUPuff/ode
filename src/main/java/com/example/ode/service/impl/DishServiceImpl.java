package com.example.ode.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.DishDao;
import com.example.ode.entity.DishEntity;
import com.example.ode.service.DishService;


@Service("dishService")
public class DishServiceImpl extends ServiceImpl<DishDao, DishEntity> implements DishService {


}
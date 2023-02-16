package com.example.ode.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.FlavorDao;
import com.example.ode.entity.FlavorEntity;
import com.example.ode.service.FlavorService;


@Service("flavorService")
public class FlavorServiceImpl extends ServiceImpl<FlavorDao, FlavorEntity> implements FlavorService {



}
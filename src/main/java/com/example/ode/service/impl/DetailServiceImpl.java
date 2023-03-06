package com.example.ode.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.DetailDao;
import com.example.ode.entity.DetailEntity;
import com.example.ode.service.DetailService;


@Service("detailService")
public class DetailServiceImpl extends ServiceImpl<DetailDao, DetailEntity> implements DetailService {


}
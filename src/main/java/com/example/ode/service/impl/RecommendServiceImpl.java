package com.example.ode.service.impl;

import com.example.ode.vo.DishVO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.RecommendDao;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.service.RecommendService;

import java.util.List;


@Service("recommendService")
public class RecommendServiceImpl extends ServiceImpl<RecommendDao, RecommendEntity> implements RecommendService {


    @Override
    public List<DishVO> recommend(Long userId) {
        return null;
    }
}
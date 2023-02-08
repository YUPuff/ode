package com.example.ode.service.impl;

import cn.hutool.http.HttpUtil;
import com.example.ode.dao.AdminDao;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import com.example.ode.entity.AdminEntity;
import com.example.ode.vo.AdminVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.service.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {


    @Value("${wxmini.appid}")
    private String appid;

    @Value("${wxmini.secret}")
    private String secret;

    @Override
    public Map<String, Object> login(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String repUrl = url.replace("APPID",appid).replace("SECRET",secret).replace("JSCODE",code);
        String s = HttpUtil.get(repUrl);
        System.out.println(s);
        Map<String,Object> map = new HashMap<>();
        map.put("message",s);
        return map;
    }

    @Override
    public AdminVO login(AdminIns adminIns) {
        return null;
    }

    @Override
    public AdminVO add(AdminIns adminIns) {
        return null;
    }


    @Override
    public AdminVO update(AdminUpd userUpd) {
        return null;
    }

    @Override
    public String delete(Integer id) {
        return null;
    }

    @Override
    public List<AdminVO> getUser(AdminSearch userSearch) {
        return null;
    }
}
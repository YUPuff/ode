package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import com.example.ode.entity.AdminEntity;
import com.example.ode.vo.AdminVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface AdminService extends IService<AdminEntity> {

    Map<String,Object> login(String code);
    AdminVO login(AdminIns adminIns);
    AdminVO add(AdminIns adminIns);
    AdminVO update(AdminUpd userUpd);
    String delete(Integer id);
    List<AdminVO> getUser(AdminSearch userSearch);
}


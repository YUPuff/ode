package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
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

    void signup(AdminIns ins);

    AdminVO login(AdminIns ins);

    void update(AdminUpd upd);

    MyPage<AdminVO> getAdmins(AdminSearch search);

    AdminVO getOneAdmin(Long id);

    AdminVO getAdminByToken(String token);

    Map<String,Object> getStatistics();

    Map<String,List> get12Data();

    Map<String,Object> index(Long id);

}


package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import com.example.ode.dto.user.UserIns;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.dto.user.UserUpd;
import com.example.ode.entity.UserEntity;
import com.example.ode.model.WXAuth;
import com.example.ode.vo.AdminVO;
import com.example.ode.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-02-08 17:35:28
 */
public interface UserService extends IService<UserEntity> {
    Map<String,Object> login1(String code);

    UserVO login2(WXAuth wxAuth);
    UserVO login(UserIns ins);
    UserVO add(UserIns ins);
    UserVO update(UserUpd upd);
    String delete(Integer id);
    List<UserVO> getUser(UserSearch search);
}


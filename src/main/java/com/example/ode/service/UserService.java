package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.dto.user.UserIns;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.dto.user.UserUpd;
import com.example.ode.entity.UserEntity;
import com.example.ode.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface UserService extends IService<UserEntity> {
    UserVO login(UserIns userIns);
    UserVO add(UserIns userIns);
    UserVO update(UserUpd userUpd);
    String delete(Integer id);
    List<UserVO> getUser(UserSearch userSearch);
}


package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.entity.UserEntity;
import com.example.ode.model.WXAuth;
import com.example.ode.vo.UserVO;

import java.util.List;

/**
 * @author yilin
 * @date 2023-02-08 17:35:28
 */
public interface UserService extends IService<UserEntity> {

    UserVO login(WXAuth wxAuth);

    void loginForToken(UserVO userVO);

    MyPage<UserVO> getUser(UserSearch search);
}


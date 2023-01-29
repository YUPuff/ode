package com.example.ode.service.impl;

import com.example.ode.dto.user.UserIns;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.dto.user.UserUpd;
import com.example.ode.vo.UserVO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.UserDao;
import com.example.ode.entity.UserEntity;
import com.example.ode.service.UserService;

import java.util.List;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Override
    public UserVO login(UserIns userIns) {
        return null;
    }

    @Override
    public UserVO add(UserIns userIns) {
        return null;
    }

    @Override
    public UserVO update(UserUpd userUpd) {
        return null;
    }

    @Override
    public String delete(Integer id) {
        return null;
    }

    @Override
    public List<UserVO> getUser(UserSearch userSearch) {
        return null;
    }
}
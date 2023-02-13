package com.example.ode.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.example.ode.dto.user.UserIns;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.dto.user.UserUpd;
import com.example.ode.model.WXAuth;
import com.example.ode.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.UserDao;
import com.example.ode.entity.UserEntity;
import com.example.ode.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Value("${wxmini.appid}")
    private String appid;

    @Value("${wxmini.secret}")
    private String secret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 第一次微信登录请求，这时服务器只能知道用户的openId（哪一个用户）,需要二次请求带上加密数据借助微信服务器获取用户更多信息
     *  1.前端微信小程序发送一串登录code
     *  2.服务器接收后借助微信小程序后台API发送请求，获得用户session_key和open_id（实际是一个字符串），
     *    自定义一个sessionId，将所有数据存在redis中
     *  3.返回给前端sessionId，便于之后二次登录标识，解密信息数据
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> login1(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String repUrl = url.replace("APPID",appid).replace("SECRET",secret).replace("JSCODE",code);
        String s = HttpUtil.get(repUrl);

        System.out.println(s);
        Map<String,Object> map = new HashMap<>();
        map.put("message",s);
        return map;
    }

    /**
     * 第二次微信登录请求
     *  1.前端发送微信小程序生成的加密数据encryptedData和iv，以及第一次登录请求存储的sessionId
     *  2.服务器解密数据，并借助微信服务器获取用户详细信息（用户名、性别、openId等）
     *  3.根据openId到数据库中查询用户，如果有则更新已有用户详细信息，没有则添加新用户
     *  4.使用jwt生成token令牌，伴随详细信息传回给前端
     * @param wxAuth
     * @return
     */
    @Override
    public UserVO login2(WXAuth wxAuth) {
        return null;
    }

    @Override
    public UserVO login(UserIns ins) {
        return null;
    }

    @Override
    public UserVO add(UserIns ins) {
        return null;
    }

    @Override
    public UserVO update(UserUpd upd) {
        return null;
    }

    @Override
    public String delete(Integer id) {
        return null;
    }

    @Override
    public List<UserVO> getUser(UserSearch search) {
        return null;
    }
}
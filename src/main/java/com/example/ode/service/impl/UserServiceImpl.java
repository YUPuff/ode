package com.example.ode.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.MyPage;
import com.example.ode.common.WxUserInfo;
import com.example.ode.constant.RedisConstant;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.model.WXAuth;
import com.example.ode.service.WxService;
import com.example.ode.util.JWTUtils;
import com.example.ode.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.UserDao;
import com.example.ode.entity.UserEntity;
import com.example.ode.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Value("${wxmini.appid}")
    private String appid;

    @Value("${wxmini.secret}")
    private String secret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WxService wxService;

    @Autowired
    private UserDao userDao;

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
    public String login1(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String repUrl = url.replace("APPID",appid).replace("SECRET",secret).replace("JSCODE",code);
        String s = HttpUtil.get(repUrl);
        // 生成唯一标识并返回给前端临时存储
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisConstant.WX_SESSION_ID+key,s,10, TimeUnit.MINUTES);
        return key;
    }

    /**
     * 第二次微信登录请求
     *  1.前端发送微信小程序生成的加密数据encryptedData和iv，以及第一次登录请求存储的唯一标识key
     *  2.服务器解密数据，并借助微信服务器获取用户详细信息（用户名、性别、openId等）
     *  3.根据openId到数据库中查询用户，如果有则更新已有用户详细信息，没有则添加新用户
     *  4.使用jwt生成token令牌，伴随详细信息传回给前端
     * @param wxAuth
     * @return
     */
    @Override
    public UserVO login2(WXAuth wxAuth) {
        try {
            String s = wxService.wxDecrypt(wxAuth.getEncryptedData(),wxAuth.getIv(),wxAuth.getKey());
            WxUserInfo userInfo = JSON.parseObject(s, WxUserInfo.class);
            String openId = userInfo.getOpenId();
            UserEntity oldUser = userDao.selectOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getOpenId, openId));
            UserEntity newUser = new UserEntity(userInfo);
            if (oldUser == null){
                // 用户不存在，注册新用户
                add(newUser);
            }else{
                // 用户存在，更新用户信息
                newUser.setId(oldUser.getId());
                update(newUser);
            }
            // 登录
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(newUser,userVO);
            return login(userVO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 利用jwt技术生成token令牌，并临时存储在redis
     * @param userVO
     * @return
     */
    public UserVO login(UserVO userVO) {
        String token = JWTUtils.sign(userVO.getId());
        redisTemplate.opsForValue().set(RedisConstant.TOKEN+token,JSON.toJSONString(userVO),7,TimeUnit.DAYS);
        userVO.setToken(token);
        return userVO;
    }

    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    public void add(UserEntity userEntity) {
        userDao.insert(userEntity);
        // 插入新记录后，userEntity的id会自动赋值
    }

    /**
     * 根据用户openId，更新用户最新信息
     * @param userEntity
     * @return
     */
    public void update(UserEntity userEntity) {
        userDao.updateById(userEntity);
    }

    @Override
    public String delete(List<Long> ids) {
        // 逻辑删除
        int res = userDao.deleteBatchIds(ids);
        if (res>0) return ResultConstant.SUCCESS;
        else return ResultConstant.FAILURE;
    }

    @Override
    public MyPage<UserVO> getUser(UserSearch search) {
        IPage<UserEntity> page = new Page<>(search.getPageNum(),10);
        IPage<UserEntity> userPage = userDao.selectPage(page, new LambdaQueryWrapper<UserEntity>()
                                                                .like(UserEntity::getName,search.getName())
                                                                .eq(UserEntity::getGender,search.getGender()));

        List<UserEntity> records = userPage.getRecords();
        List<UserVO> list = new ArrayList<>();
        for (UserEntity userEntity:records) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userEntity,userVO);
            list.add(userVO);
        }

        return null;
    }
}
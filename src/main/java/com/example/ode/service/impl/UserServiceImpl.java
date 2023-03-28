package com.example.ode.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.common.WxUserInfo;
import com.example.ode.constant.RedisConstants;
import com.example.ode.constant.ResultConstants;
import com.example.ode.dto.user.UserSearch;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.model.WXAuth;
import com.example.ode.service.RecommendService;
import com.example.ode.service.WxService;
import com.example.ode.util.EncryptUtils;
import com.example.ode.util.JWTUtils;
import com.example.ode.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.UserDao;
import com.example.ode.entity.UserEntity;
import com.example.ode.service.UserService;
import org.springframework.transaction.annotation.Transactional;

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
    private RecommendService recommendService;

    @Autowired
    private UserDao userDao;

    /**
     * 1.前端微信小程序发送一串登录code，以及微信小程序生成的加密数据encryptedData和iv
     * 2.服务器接收后借助微信小程序后台API发送请求（此时使用code），获得用户session_key和open_id（实际是一个字符串）
     * 3.服务器解密数据encryptedData和iv，以及之前生成的session_key，借助微信服务器获取用户详细信息（用户名、性别、openId等）
     * 4.根据openId到数据库中查询用户，如果有则更新已有用户详细信息，没有则添加新用户
     * 5.使用jwt生成token令牌，伴随详细信息传回给前端
     * @param wxAuth
     * @return
     */
    @Override
    public UserVO login(WXAuth wxAuth) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String repUrl = url.replace("APPID",appid).replace("SECRET",secret).replace("JSCODE", wxAuth.getCode());
        // 生成包含session_key和open_id的json字符串
        String s = HttpUtil.get(repUrl);
        try {
            // 借助微信平台解密后生成包含用户详细信息的json字符串
            String info_s = wxService.wxDecrypt(wxAuth.getEncryptedData(),wxAuth.getIv(),s);
            WxUserInfo userInfo = JSON.parseObject(info_s, WxUserInfo.class);
            // 对用户openid先加密，再进行存储
            userInfo.setOpenId(EncryptUtils.MD5EncryptMethod(userInfo.getOpenId()));
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
            loginForToken(userVO);
            System.out.println(userVO);
            return userVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 利用jwt技术生成token令牌，并临时存储在redis
     * @param userVO
     * @return
     */
    @Override
    public void loginForToken(UserVO userVO) {
        String token = JWTUtils.sign(userVO.getId());
//        redisTemplate.opsForValue().set(RedisConstants.TOKEN+token,JSON.toJSONString(userVO),7,TimeUnit.DAYS);
        userVO.setToken(token);
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


    /**
     * 分页查询普通用户
     * @param search
     * @return
     */
    @Override
    public MyPage<UserVO> getUser(UserSearch search) {
        IPage<UserEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<UserVO> userPage = userDao.selectMyPage(page, new LambdaQueryWrapper<UserEntity>()
                .like(StringUtils.isNotBlank(search.getName()),UserEntity::getName,search.getName())
                .eq(StringUtils.isNotBlank(search.getGender()),UserEntity::getGender,search.getGender()));

        MyPage<UserVO> myPage = MyPage.createPage(userPage);

        return myPage;
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            // 判断用户是否存在
            UserEntity user = userDao.selectById(id);
            if (user == null)
                throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
            // 删除推荐表中用户相关记录
            recommendService.remove(new LambdaQueryWrapper<RecommendEntity>().eq(RecommendEntity::getUserId,id));
            // 删除用户
            userDao.deleteById(id);
        }

    }
}
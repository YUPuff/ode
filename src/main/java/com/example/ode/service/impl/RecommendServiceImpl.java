package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyRecommender;
import com.example.ode.constant.RecommenderConstants;
import com.example.ode.constant.ResultConstants;
import com.example.ode.dto.dish.DishDTO;
import com.example.ode.entity.UserEntity;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderDishService;
import com.example.ode.service.UserService;
import com.example.ode.vo.DishVO;
import com.example.ode.vo.RecommendVO;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.common.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.RecommendDao;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.service.RecommendService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("recommendService")
public class RecommendServiceImpl extends ServiceImpl<RecommendDao, RecommendEntity> implements RecommendService {

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private RecommendDao recommendDao;

    private static final double threshold = 0.59f;

    private static final Integer neighbor = 305;

    /**
     * 根据用户id，为其推荐菜品
     * @param userId
     * @return
     * @throws TasteException
     */

    @Override
    public List<DishVO> recommend(Long userId) throws Exception {
        // 检验用户是否存在
        UserEntity user = userService.getById(userId);
        if (user == null)
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);

        // 判断用户是否是新用户
        RecommendEntity recommendEntity = recommendDao.selectOne(new LambdaQueryWrapper<RecommendEntity>().eq(RecommendEntity::getUserId, userId));
        if (recommendEntity == null)
            return orderDishService.getTop5Dishes();

        // 1. 创建带有数据模型的自定义实体对象
        MyRecommender myRecommender = MyRecommender.build();
        // 2. 根据dataModel和指定的相似度量方法(谷本系数)生成用户相似度，并创建基于用户的推荐生成器(不完整)
        MyRecommender.UserBaseRecommender userBaseRecommender = myRecommender.getUserBaseRecommender(RecommenderConstants.SIMILARITY_CITY_BLOCK);
        // 3. 根据dataModel和similarity生成用户邻居，完善推荐生成器
        userBaseRecommender.getNearestUserNeighborhood(neighbor);
        // 4. 根据生成器创建通用推荐引擎，参数为false表示无偏好值
        MyRecommender.CommonRecommender commonRecommender = userBaseRecommender.getCommonRecommender(false);
        // 5. 生成推荐
        List<RecommendedItem> recommend = commonRecommender.recommend(userId, 10);
        List<DishVO> dishes = new ArrayList<>();
        for (RecommendedItem recommendedItem : recommend) {
            DishVO dish = dishService.getOneDish(recommendedItem.getItemID());
            dishes.add(dish);
        }
        return dishes;
    }

    @Override
    public List<RecommendVO> getRecordsForUser(Long userId) {
        // 检查用户是否存在
        UserEntity user = userService.getById(userId);
        if (user == null)
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
        return recommendDao.getRecordsForUser(userId);
    }


}
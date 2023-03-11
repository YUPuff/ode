package com.example.ode;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ode.common.MyRecommender;
import com.example.ode.constant.RecommenderConstants;
import com.example.ode.dao.DetailDao;
import com.example.ode.dao.OrderDishDao;
import com.example.ode.dao.RecommendDao;
import com.example.ode.entity.DetailEntity;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.service.RecommendService;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.common.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;


@SpringBootTest
class OdeApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RecommendService recommendService;


    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("k1","v1");
    }

//    @Test
//    void recommend(){
//        List<DetailEntity> detailEntities = detailDao.selectList(new QueryWrapper<DetailEntity>());
//        for (DetailEntity detailEntity : detailEntities) {
//            Long userId = detailEntity.getUserId();
//            Long dishId = detailEntity.getDishId();
//            Integer count = detailEntity.getCount();
//            RecommendEntity recommendEntity = recommendDao.selectOne(new LambdaQueryWrapper<RecommendEntity>()
//                    .eq(RecommendEntity::getUserId,userId).eq(RecommendEntity::getDishId,dishId));
//            if (recommendEntity == null){
//                recommendEntity = new RecommendEntity();
//                recommendEntity.setDishId(dishId);
//                recommendEntity.setUserId(userId);
//                recommendEntity.setCount(count);
//                recommendDao.insert(recommendEntity);
//            }else{
//                recommendEntity.setCount(recommendEntity.getCount()+count);
//                recommendDao.update(recommendEntity,new LambdaQueryWrapper<RecommendEntity>()
//                        .eq(RecommendEntity::getUserId,userId).eq(RecommendEntity::getDishId,dishId));
//            }
//        }
//    }

    @Test
    public void recommend() throws Exception {
        Long userId = 982L;
        MyRecommender myRecommender = MyRecommender.build();
        // 2. 根据dataModel和指定的相似度量方法生成用户相似度，并创建基于用户的推荐生成器(不完整)
        MyRecommender.UserBaseRecommender userBaseRecommender = myRecommender.getUserBaseRecommender(RecommenderConstants.SIMILARITY_CITY_BLOCK);
        // 3. 根据dataModel和similarity生成用户邻居，完善推荐生成器
        userBaseRecommender.getNearestUserNeighborhood(10);
        // 4. 根据生成器创建通用推荐引擎，参数为false表示无偏好值
        MyRecommender.CommonRecommender commonRecommender = userBaseRecommender.getCommonRecommender(false);
        // 5. 生成推荐
        List<RecommendedItem> recommend = commonRecommender.recommend(userId, 10);
        for (RecommendedItem recommendedItem : recommend) {
            System.out.println(recommendedItem);
        }

        // 评估结果


    }
}

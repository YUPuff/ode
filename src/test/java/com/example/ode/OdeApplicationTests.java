package com.example.ode;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ode.dao.DetailDao;
import com.example.ode.dao.OrderDishDao;
import com.example.ode.dao.RecommendDao;
import com.example.ode.entity.DetailEntity;
import com.example.ode.entity.RecommendEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;


@SpringBootTest
class OdeApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;



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

}

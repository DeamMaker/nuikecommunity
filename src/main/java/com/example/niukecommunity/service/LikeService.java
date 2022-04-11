package com.example.niukecommunity.service;

import com.example.niukecommunity.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId,int entityType,int entityId,int entityUserId){
//        String entityLikeKey= RedisKeyUtil.getEntityLikeKey(entityType,entityId);
//        boolean isMember=redisTemplate.opsForSet().isMember(entityLikeKey,userId);
//        //若为true,则意味着已经点过赞了
//        if(isMember){
//            //再点一下就意味着取消点赞
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }
//        //若为false,说明点一下就点赞了
//        else{
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }

        //代码重构，使用编程式保证在一个是事务中
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey= RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey=RedisKeyUtil.getUserLikeKey(entityUserId);
                boolean isMember=redisTemplate.opsForSet().isMember(entityLikeKey,userId);
                //所有的判断操作需要在事务开启之后再进行，否则不会立即执行，而是会放在队列中

                //开启事务
                operations.multi();
                if(isMember){
                    operations.opsForSet().remove(entityLikeKey,userId);
                    operations.opsForValue().decrement(userLikeKey);
                }else{
                    operations.opsForSet().add(entityLikeKey,userId);
                    operations.opsForValue().increment(userLikeKey);
                }
                //提交事务
                return operations.exec();
            }
        });
    }
    //查询点赞的数量
    public long findEntityLikeCount(int entityType,int entityId){
        //获取key
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }
    //查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId,int entityType,int entityId){
        //获取Key
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
    }

    //查询某个用户获得的赞
    public int findUserLikeCount(int userId){
        String userLikeKey=RedisKeyUtil.getUserLikeKey(userId);
        Integer count=(Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count==null?0:count.intValue();
    }
}

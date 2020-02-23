package com.wzn.ablog.article.service;

import org.springframework.data.redis.core.RedisTemplate;

public class BaseService {

    //清除全部缓存
    public void clearRedis(Integer totaPage, String userId, RedisTemplate redisTemplate){
        for (int i = 1;i<totaPage;i++){
            redisTemplate.delete("articles" + userId+i);
        }
    }
}

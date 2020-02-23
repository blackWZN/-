package com.wzn.ablog.article.service;

import com.wzn.ablog.article.dao.CategoryDao;
import com.wzn.ablog.article.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public Page<Category> list(Integer page,Integer limit){
        return categoryDao.findAll(PageRequest.of(page-1,limit));
    }

    public String findNameById(String id){
        String name = (String) redisTemplate.opsForValue().get(id);
        if(name == null){
            name =  categoryDao.findNameById(id);
            redisTemplate.opsForValue().set(id,name);
        }else {
            return name;
        }
        return name;
    }
}

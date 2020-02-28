package com.wzn.ablog.article.service;

import com.wzn.ablog.article.dao.ArticleDao;
import com.wzn.ablog.article.feign.AdminFeign;
import com.wzn.ablog.common.entity.Article;
import com.wzn.ablog.common.utils.IdWorker;
import com.wzn.ablog.common.utils.RedisUtils;
import com.wzn.ablog.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    private Integer totaPage;

    //加载列表
    public Page<Article> list(Integer page, Integer limit, String userId) {
        Page<Article> list = (Page<Article>) redisTemplate.opsForValue().get("articles" + userId + page);
        if (list == null) {
            list = articleDao.findAll(PageRequest.of(page - 1, limit, Sort.by("updateTime").descending()));
            totaPage = list.getTotalPages();
            redisTemplate.opsForValue().set("articles" + userId + page, list);
        } else {
            return list;
        }
        return list;
    }

    //删除
    public void del(String[] ids, String uId) {
        for (String id : ids) {
            articleDao.deleteById(id);
        }

        RedisUtils.clearRedis(totaPage, uId, redisTemplate);

    }

    //添加
    public void add(Article reqArticle, String username, String userId) {
        String isOriginal = reqArticle.getIs_original();
        String recommended = reqArticle.getRecommended();
        Article article = new Article()
                .setId(String.valueOf(idWorker.nextId()))
                .setTitle(reqArticle.getTitle())
                .setIntro(reqArticle.getIntro())
                .setCategory_id(reqArticle.getCategory_id())
                .setIs_original(isOriginal == null ? "0" : isOriginal.equals("on") ? "1" : "0")
                .setRecommended(recommended == null ? "0" : recommended.equals("on") ? "1" : "0")
                .setStatus(reqArticle.getStatus().equals("on") ? "1" : "0")//1通过 0待审
                .setContent(reqArticle.getContent())
                .setCreate_time(new Date())
                .setUpdateTime(new Date())
                .setUsername(username);
        articleDao.save(article);
        RedisUtils.clearRedis(totaPage, userId, redisTemplate);//清除缓存
    }

    //更新
    public void update(Article article, String userId) {
        articleDao.delete(article);
        articleDao.save(article);
        RedisUtils.clearRedis(totaPage, userId, redisTemplate);
    }

    //查找全部
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    //根据id查找文章
    public Article findById(String id) {
        Optional<Article> article = articleDao.findById(id);
        return article.get();
    }
}

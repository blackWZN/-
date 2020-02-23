package com.wzn.azblog.search.quartz;

import com.alibaba.fastjson.JSON;
import com.wzn.ablog.common.vo.Result;
import com.wzn.azblog.search.dao.EsActicleDao;
import com.wzn.azblog.search.entity.Article;
import com.wzn.azblog.search.entity.EsArticle;
import com.wzn.azblog.search.feign.SearchFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;


@Slf4j
@Component
public class EsQuartz {

    @Autowired
    private SearchFeign searchServce;

    @Autowired
    private EsActicleDao esActicleDao;

//    @Scheduled(fixedRate = 10000)//每10秒执行一次
//    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
    @Scheduled(cron = "0 0 0/1 * * ? ") // 每一小时执行一次
    public void syncArticle() {
        System.out.println("同步索引库");
        Result all = searchServce.findAll();

        String s = JSON.toJSONString(all.getData());
        List<Article> articles = JSON.parseArray(s, Article.class);

        articles.stream().forEach(item -> {
            EsArticle esArticle = new EsArticle()
                    .setContent(item.getContent())
                    .setId(item.getId())
                    .setIntro(item.getIntro())
                    .setNickname(item.getNickname())
                    .setTitle(item.getTitle())
                    .setStatus(item.getStatus())
                    .setUpdateTime(item.getUpdate_time())
                    .setCategory_id(item.getCategory_id())
                    .setIs_original(item.getIs_original())
                    .setRecommended(item.getRecommended());
            esActicleDao.save(esArticle);
     });
    }
}

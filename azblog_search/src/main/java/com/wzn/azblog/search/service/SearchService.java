package com.wzn.azblog.search.service;

import com.netflix.discovery.converters.Auto;
import com.wzn.azblog.search.dao.EsActicleDao;
import com.wzn.azblog.search.entity.EsArticle;
import com.wzn.azblog.search.feign.SearchFeign;
import com.wzn.azblog.search.quartz.EsQuartz;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SearchService {

    @Autowired
    private EsActicleDao esActicleDao;

    public Page<EsArticle> search(String keywords, Integer page, Integer limit) {
        //should：或者
       // fuzzyQuery：模糊查询
        QueryBuilder queryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.fuzzyQuery("intro",keywords))
                .should(QueryBuilders.fuzzyQuery("nickname",keywords))
                .should(QueryBuilders.fuzzyQuery("title",keywords))
                .should(QueryBuilders.fuzzyQuery("content",keywords));

        //分页
        PageRequest pageRequest = PageRequest.of(page-1, limit);
        Page<EsArticle> pageInfo = esActicleDao.search(queryBuilder, pageRequest);
        return pageInfo;
    }
}

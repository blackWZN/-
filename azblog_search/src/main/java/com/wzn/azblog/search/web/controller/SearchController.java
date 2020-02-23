package com.wzn.azblog.search.web.controller;

import com.wzn.ablog.common.vo.PageResult;
import com.wzn.azblog.search.entity.EsArticle;
import com.wzn.azblog.search.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ArticleService articleService;

    //文章搜索
    @GetMapping("/{keywords}/{page}/{limit}")
    public PageResult search(@PathVariable("keywords") String keywords, @PathVariable Integer page, @PathVariable Integer limit){
        Page<EsArticle> pageInfo = articleService.search(keywords, page, limit);
        return new PageResult("0","搜索成功",pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),pageInfo.getContent());
    }
}

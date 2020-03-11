package com.wzn.azblog.search.web.controller;

import com.wzn.ablog.common.vo.PageResult;
import com.wzn.azblog.search.entity.EsArticle;
import com.wzn.azblog.search.entity.EsCategory;
import com.wzn.azblog.search.entity.EsComment;
import com.wzn.azblog.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService articleService;

    //文章搜索
    @GetMapping("/{keywords}/{page}/{limit}")
    public PageResult search(@PathVariable("keywords") String keywords, @PathVariable Integer page, @PathVariable Integer limit){
        Page<EsArticle> pageInfo = articleService.searchArticle(keywords, page, limit);
        return new PageResult("0","搜索成功",pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),pageInfo.getContent());
    }

    //分类搜索搜索
    @GetMapping("/categroy/{keywords}/{page}/{limit}")
    public PageResult searchCategroy(@PathVariable("keywords") String keywords, @PathVariable Integer page, @PathVariable Integer limit){
        Page<EsCategory> pageInfo = articleService.searchCategroy(keywords, page, limit);
        return new PageResult("0","搜索成功",pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),pageInfo.getContent());
    }

    //评论搜索搜索
    @GetMapping("/comment/{keywords}/{page}/{limit}")
    public PageResult searchComment(@PathVariable("keywords") String keywords, @PathVariable Integer page, @PathVariable Integer limit){
        Page<EsComment> pageInfo = articleService.searchComment(keywords, page, limit);
        return new PageResult("0","搜索成功",pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),pageInfo.getContent());
    }
}

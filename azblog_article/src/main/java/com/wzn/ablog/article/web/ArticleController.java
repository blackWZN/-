package com.wzn.ablog.article.web;

import com.wzn.ablog.article.feign.SearchFeign;
import com.wzn.ablog.article.service.ArticleService;
import com.wzn.ablog.common.entity.Article;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
@Api(tags = {"文章管理模块"})
public class ArticleController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SearchFeign searchFeign;

    @ApiOperation(value = "文章列表", notes = "获取文章列表并分页")
    @ApiImplicitParam(name = "Authorization", value = "令牌", required = true)
    @GetMapping
    public PageResult list(Integer page, Integer limit) {
        Page<Article> list = articleService.list(page, limit, TokenUtils.getUserId(request,rsaKeyConfig));
        return new PageResult("0", "列表加载完成", list.getTotalElements(),
                list.getTotalPages(), list.getContent());
    }

    @ApiOperation("根据id查找文章")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Article article = articleService.findById(id);
        return new Result("200", "查找到文章", article);
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("/{ids}")
    public Result del(@PathVariable("ids") String[] ids) {
        articleService.del(ids, TokenUtils.getUserId(request,rsaKeyConfig));
        return new Result("200", "删除成功");
    }

    @ApiOperation("添加文章")
    @PostMapping
    public Result add(@RequestBody Article article) {
        log.debug(String.valueOf(article));
        articleService.add(article, TokenUtils.getUsername(request,rsaKeyConfig)
        ,TokenUtils.getUserId(request,rsaKeyConfig));
        return new Result("200", "添加成功");
    }

    @ApiOperation("更新文章")
    @PutMapping("update")
    public Result update(@RequestBody Article article) {
        articleService.update(article, TokenUtils.getUserId(request,rsaKeyConfig));
        return new Result("200", "更新成功");
    }

    @ApiOperation("查找全部文章")
    @GetMapping("/findAll")
    public Result findAll() {
        List<Article> all = articleService.findAll();
        return new Result("200", "查询成功", all);
    }

    @ApiOperation(value = "搜索",notes = "搜索文章并分页")
    @GetMapping("/{keywords}/{page}/{limit}")
    public PageResult search(@PathVariable("keywords") String keywords, @PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return searchFeign.search(keywords, page, limit);
    }

}

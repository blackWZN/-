package com.wzn.ablog.article.web;

import com.wzn.ablog.article.entity.Category;
import com.wzn.ablog.article.service.CategoryService;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PageResult list(Integer page, Integer limit){
        Page<Category> pageInfo = categoryService.list(page, limit);
        return new PageResult("0","列表加载成功",pageInfo.getTotalElements()
        ,pageInfo.getTotalPages(),pageInfo.getContent());
    }

    @GetMapping("/{id}")
    public Result findNameById(@PathVariable String id){
        String name = categoryService.findNameById(id);
        if(name != null){
            return new Result("200","已查找到分类名称",name);
        }
        return new Result("200","未查找到分类名称");
    }
}

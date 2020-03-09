package com.wzn.ablog.article.web;

import com.wzn.ablog.article.service.CategoryService;
import com.wzn.ablog.common.entity.Category;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查找全部
    @GetMapping
    public PageResult list(Integer page, Integer limit){
        Page<Category> pageInfo = categoryService.list(page, limit);
        return new PageResult("0","列表加载成功",pageInfo.getTotalElements()
        ,pageInfo.getTotalPages(),pageInfo.getContent());
    }

    //根据id查找
    @GetMapping("/{id}")
    public Result findNameById(@PathVariable String id){
        String name = categoryService.findNameById(id);
        if(name != null){
            return new Result("200","已查找到分类名称",name);
        }
        return new Result("200","未查找到分类名称");
    }

    @DeleteMapping("/{ids}")
    public AzResult del(@PathVariable String[] ids){
        categoryService.del(ids);
        return AzResult.ok("删除成功");
    }

    @PostMapping
    public AzResult add(@RequestBody Category category){
        categoryService.add(category);
        return AzResult.ok("添加成功");
    }

    @PutMapping
    public AzResult update(@RequestBody Category category){
        categoryService.update(category);
        return AzResult.ok("更新成功");
    }
}

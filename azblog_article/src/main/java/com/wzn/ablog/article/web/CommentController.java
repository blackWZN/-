package com.wzn.ablog.article.web;

import com.wzn.ablog.article.service.CommentService;
import com.wzn.ablog.common.entity.Comment;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public PageResult list(int page,int limit){
        Page<Comment> pageInfo = commentService.list(page, limit);
        return  new PageResult("0","列表加载成功",pageInfo.getTotalElements()
                ,pageInfo.getTotalPages(),pageInfo.getContent());
    }

    @GetMapping("/{id}")
    public AzResult findbyId(@PathVariable String id){
        Comment comment = commentService.findById(id);
        return AzResult.ok().data(comment);
    }

    @DeleteMapping("/{ids}")
    public AzResult del(@PathVariable String[] ids){
        commentService.del(ids);
        return AzResult.ok("删除成功");
    }

    @PostMapping
    public AzResult add(@RequestBody Comment comment){
        commentService.add(comment);
        return AzResult.ok("添加成功");
    }

    @PutMapping
    public AzResult update(@RequestBody Comment comment){
        commentService.update(comment);
        return AzResult.ok("更新成功");
    }
}

package com.wzn.azblog.base.web.controller;

import com.wzn.ablog.common.entity.Link;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.azblog.base.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping
    public PageResult list(int page,int limit) {
        Page<Link> list = linkService.list(page, limit);
        return new PageResult("0", "列表加载完成", list.getTotalElements(),
                list.getTotalPages(), list.getContent());
    }

    @PostMapping
    public AzResult add(@RequestBody Link link){
        linkService.add(link);
        return AzResult.ok();
    }

    @DeleteMapping("/{ids}")
    public AzResult del(@PathVariable String[] ids){
        linkService.del(ids);
        return AzResult.ok();
    }

    @GetMapping("/{id}")
    public AzResult findById(@PathVariable String id){
        Link link =  linkService.findById(id);
        return AzResult.ok().data(link);
    }

    @GetMapping("/search/{keywords}")
    public AzResult search(@PathVariable String keywords){
        List<Link> list = linkService.search(keywords);
        return AzResult.ok().data(list);
    }
}

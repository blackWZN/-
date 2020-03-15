package com.wzn.azblog.Statistics.web.controller;

import com.wzn.ablog.common.entity.EchartsDimensions;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.EchartsResult;
import com.wzn.azblog.Statistics.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/dataset")
    public AzResult dimensions(){
        EchartsResult list = echartsService.list();
        return AzResult.ok().data(list);
    }

    @GetMapping("/onlineCount")
    public AzResult onlineCount(){
        Object onlineCount = redisTemplate.opsForValue().get("onlineCount");
        return AzResult.ok().data(onlineCount);
    }
}

package com.wzn.azblog.Statistics.web.controller;

import com.wzn.ablog.common.entity.EchartsDimensions;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.EchartsResult;
import com.wzn.azblog.Statistics.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsDimensionsController {

    @Autowired
    private EchartsService echartsService;

    @GetMapping("/dataset")
    public AzResult dimensions(){
        EchartsResult list = echartsService.list();
        return AzResult.ok().data(list);
    }
}

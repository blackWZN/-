package com.wzn.azblog.base.web.controller;

import com.wzn.ablog.common.entity.WebInfo;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.Result;
import com.wzn.azblog.base.service.WebInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webInfo")
public class WebInfoController {

    @Autowired
    private WebInfoService webInfoService;

    @GetMapping
    public AzResult showInfo(){
        List<WebInfo> webInfo = webInfoService.showInfo();
        return AzResult.ok().data(webInfo);
    }

    @PutMapping
    public AzResult updateInfo(String content){
        webInfoService.updateInfo(content);
        return AzResult.ok();
    }
}

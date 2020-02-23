package com.wzn.azblog.search.feign;

import com.wzn.ablog.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "azblog-article")
public interface SearchFeign{

    @GetMapping("/article/findAll")
    Result findAll();
}

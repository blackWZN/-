package com.wzn.ablog.article.feign;

import com.wzn.ablog.common.vo.Result;
import org.springframework.stereotype.Component;

@Component
public class AdminFeignFallback implements AdminFeign {
    @Override
    public Result getName(String id) {
        return new Result("500","无法获取用户名");
    }
}

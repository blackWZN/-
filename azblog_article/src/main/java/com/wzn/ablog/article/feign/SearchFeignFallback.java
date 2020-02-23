package com.wzn.ablog.article.feign;

import com.wzn.ablog.common.vo.PageResult;
import org.springframework.stereotype.Component;

@Component
public class SearchFeignFallback implements SearchFeign {
    @Override
    public PageResult search(String keywords, Integer page, Integer limit) {
        return new PageResult("500","搜索服务不可用");
    }
}

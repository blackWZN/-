package com.wzn.ablog.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    private String status;
    private String message;
    private Long total;
    private int page;
    private Object data;

    public PageResult(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

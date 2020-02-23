package com.wzn.ablog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String status;
    private String message;
    private Object data;

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

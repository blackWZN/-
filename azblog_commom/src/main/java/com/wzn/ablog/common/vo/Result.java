package com.wzn.ablog.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result {
    private String status;
    private String message;
    private Object data;

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

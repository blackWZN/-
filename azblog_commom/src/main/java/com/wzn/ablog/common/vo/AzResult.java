package com.wzn.ablog.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class AzResult {

    private int status;
    private String message;
    private Object data;

    private AzResult() {
    }

    public static AzResult ok(){
        return ok("操作成功");
    }

    public static AzResult ok(String msg){
        return ok(200,msg);
    }

    public static AzResult ok(int status, String msg) {
        AzResult azResult = new AzResult();
        azResult.setStatus(status);
        azResult.setMessage(msg);
        return azResult;
    }

    public static AzResult err(){
        return err("操作失败");
    }

    public static AzResult err(String msg){
        return err(500,msg);
    }

    public static AzResult err(int status, String msg) {
        AzResult azResult = new AzResult();
        azResult.setStatus(status);
        azResult.setMessage(msg);
        return azResult;
    }

    public AzResult data(Object obj){
        this.data = obj;
        return this;
    }


}

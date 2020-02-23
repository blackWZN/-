package com.wzn.ablog.admin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzn.ablog.admin.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class blogUtils {

    public static void respMsg(HttpServletResponse response, String status, String msg, Object data) {
        try {
            Result result = new Result(status, msg, data);
            ObjectMapper mapper = new ObjectMapper();
            String res = mapper.writeValueAsString(result);
            response.setContentType("text/html; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.wzn.ablog.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzn.ablog.common.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

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

    public static String code(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<6;i++) {
            stringBuffer.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String code = stringBuffer.toString().toUpperCase();
        request.getSession().setAttribute("code", code);
        return code;
    }

    public static String getCode(HttpServletRequest request){
        String code = (String) request.getSession().getAttribute("code");
        request.getSession().invalidate();
        return code;
    }
}

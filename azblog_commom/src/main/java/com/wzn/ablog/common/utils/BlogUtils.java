package com.wzn.ablog.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzn.ablog.common.vo.Result;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlogUtils {

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

    //转换实体类
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz){
        List<T> returnList = new ArrayList<T>();
        if(CollectionUtils.isEmpty(list)){
            return returnList;
        }
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];
        //确定构造方法
        for (int i = 0; i < co.length; i++) {
            if(co[i]!=null){
                c2[i] = co[i].getClass();
            }else {
                c2[i]=String.class;
            }
        }
        for (Object[] o : list) {
            Constructor<T> constructor = null;
            try {
                constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return returnList;
    }

    public static String getWeek(DayOfWeek dayOfWeek) {
        String week = dayOfWeek.toString().toLowerCase();
        if (week.equals("monday")) {
            return "周一";
        } else if (week.equals("tuesday")) {
            return "周二";
        } else if (week.equals("tuesday")) {
            return "周三";
        } else if (week.equals("thursday")) {
            return "周四";
        } else if (week.equals("friday")) {
            return "周五";
        } else if (week.equals("saturday")) {
            return "周六";
        } else if (week.equals("weekday")) {
            return "周日";
        } else {
            return null;
        }

    }

    public static String getWeekNum(String weekDay) {
        if (weekDay.equals("周一")) {
            return "1";
        } else if (weekDay.equals("周二")) {
            return "2";
        } else if (weekDay.equals("周三")) {
            return "3";
        } else if (weekDay.equals("周四")) {
            return "4";
        } else if (weekDay.equals("周五")) {
            return "5";
        } else if (weekDay.equals("周六")) {
            return "6";
        } else if (weekDay.equals("周日")) {
            return "7";
        } else {
            return null;
        }
    }
}
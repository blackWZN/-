package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.config.RsaKeyConfig;
import com.wzn.ablog.admin.entity.Admin;
import com.wzn.ablog.admin.entity.Role;
import com.wzn.ablog.admin.utils.JwtUtils;
import com.wzn.ablog.admin.vo.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    //获取权限
    public String getRoles(){
        String token = request.getHeader("Authorization");
        log.debug(token);
        Payload<Admin> info = JwtUtils.getInfoFromToken(token, rsaKeyConfig.getPublicKey(), Admin.class);
        List<Role> roles = info.getUserInfo().getRoles();
        String rolename = "";
        for(Role role : roles){
            rolename += role.getRole_name()+",";
        }
        return rolename.substring(0,rolename.length()-1);
    }

    //获取用户id
    public String getUserId(){
        String token = request.getHeader("Authorization");
        log.debug(token);
        Payload<Admin> info = JwtUtils.getInfoFromToken(token, rsaKeyConfig.getPublicKey(), Admin.class);
        return info.getUserInfo().getId();
    }

    //获取用户id
    public String getUsername(){
        String token = request.getHeader("Authorization");
        log.debug(token);
        Payload<Admin> info = JwtUtils.getInfoFromToken(token, rsaKeyConfig.getPublicKey(), Admin.class);
        return info.getUserInfo().getUsername();
    }
}

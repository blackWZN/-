package com.wzn.ablog.article.web;

import com.wzn.ablog.article.config.RsaKeyConfig;
import com.wzn.ablog.article.entity.Admin;
import com.wzn.ablog.common.utils.JwtUtils;
import com.wzn.ablog.common.vo.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    public String getUserId(){
        String token = request.getHeader("Authorization");
        log.debug(token);
        Payload<Admin> info = JwtUtils.getInfoFromToken(token, rsaKeyConfig.getPublicKey(), Admin.class);
        return info.getUserInfo().getId();
    }
}

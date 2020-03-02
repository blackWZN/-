package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.AdminService;
import com.wzn.ablog.admin.vo.Result;
import com.wzn.ablog.common.entity.Admin;
import com.wzn.ablog.common.utils.JwtUtils;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import com.wzn.ablog.common.vo.PageResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    @Autowired
    private AdminService adminService;

    @PostMapping("/apply")
    public Result apply(@RequestBody Admin admin) {
        adminService.apply(admin);
        return new Result("200", "你的账号申请已提交，审核结果会发送到您的邮箱");
    }

    @GetMapping
    public PageResult getAdminList(int page, int limit) {
        Page<Admin> list = adminService.getAdminList(page, limit);
        return new PageResult("0", "成功", list.getTotalElements(),
                list.getTotalPages(), list.getContent());
    }


    @DeleteMapping("/{userId}")
    public String del(@PathVariable("userId") String userId) {
        return "del";
    }

    //重置密码
    @PutMapping("/resetPwd/{id}")
    public Result resetPwd(@PathVariable String id) {
        adminService.resetPwd(id);
        return new Result("200", "密码重置成功");
    }

    //获取用户名
    @PostMapping("/getUserame/{id}")
    public Result getUsernameById(@PathVariable String id) {
        log.debug(TokenUtils.getUserId(request, rsaKeyConfig));
        String username = adminService.getUsernameById(TokenUtils.getUserId(request, rsaKeyConfig));
        if (username != null) {
            return new Result("200", "已获取到用户名", username);
        }
        return new Result("500", "请登录后操作");
    }

    //根据token获取用户名
    @GetMapping("/getUsername")
    public Result getUsernameByToken() {
        return new Result("200", "获取到用户名", TokenUtils.getUsername(request, rsaKeyConfig));
    }

    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        JwtUtils.parserToken(token,rsaKeyConfig.getPublicKey());
        return new Result("200","token未过期");

    }
}

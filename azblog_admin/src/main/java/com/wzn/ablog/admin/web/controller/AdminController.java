package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.AdminService;
import com.wzn.ablog.common.annotation.Authorized;
import com.wzn.ablog.common.entity.Admin;
import com.wzn.ablog.common.utils.JwtUtils;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import com.wzn.ablog.common.utils.blogUtils;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MailSender mailSender;

    //邮件验证码
    @GetMapping("/code")
    public AzResult code(String phone) {
        String code = blogUtils.code(request);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("AZ博客");
        message.setText("注册验证码为：" + code);
        message.setTo(phone);
        message.setFrom("1478981855@qq.com");
        mailSender.send(message);
        return AzResult.ok().data("验证码已发送到您的邮箱");
    }

    //注册
    @PostMapping("/apply")
    public AzResult apply(@RequestBody Map<String, String> params) {
        String code = blogUtils.getCode(request);
        for (Map.Entry<String, String> map : params.entrySet()){
            if(map.getKey().equals("code")){
               if(map.getValue().equals(code)){
                   adminService.apply(params);
                   return AzResult.ok("注册成功");
               }
            }
        }
        return AzResult.err("验证码错误");
    }

    //加载用户列表
    @Authorized
    @GetMapping
    public PageResult getAdminList(int page, int limit) {
        Page<Admin> list = adminService.getAdminList(page, limit);
        return new PageResult("0", "成功", list.getTotalElements(),
                list.getTotalPages(), list.getContent());
    }

    //添加用户
    @Authorized
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        boolean isRepetitive = adminService.adminIsRepetitive(admin.getUsername());
        if (!isRepetitive) {
            adminService.add(admin);
            return new Result("200", "添加成功");
        }
        return new Result("500", "用户名重复");
    }

    //重置密码
    @Authorized
    @PutMapping("/resetPwd/{id}")
    public Result resetPwd(@PathVariable String id) {
        adminService.resetPwd(id);
        return new Result("200", "密码重置成功");
    }

    //根据id查找用户
    @Authorized
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Admin admin = adminService.findById(id);
        return new Result("200", "查询成功", admin);
    }

    //获取用户名
    @Authorized
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

    //刷新token
    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        JwtUtils.parserToken(token, rsaKeyConfig.getPublicKey());
        return new Result("200", "token未过期");
    }

    //根据id删除
    @Authorized
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        boolean b = adminService.delete(id);
        if (b) {
            return new Result("200", "删除成功");
        }
        return new Result("500", "root用户不可删除");
    }

    //根据用户名搜索
    @GetMapping("search/{username}")
    public AzResult searchByUsername(@PathVariable String username){
        List<Admin> admins = adminService.searchByUsername(username);
        return AzResult.ok().data(admins);
    }
}

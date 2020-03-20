package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.AdminService;
import com.wzn.ablog.common.annotation.Authorized;
import com.wzn.ablog.common.entity.Admin;
import com.wzn.ablog.common.utils.JwtUtils;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import com.wzn.ablog.common.utils.BlogUtils;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"用户"})
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MailSender mailSender;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/code")
    public AzResult code(String phone) {
        String code = BlogUtils.code(request);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("AZ博客");
        message.setText("注册验证码为：" + code);
        message.setTo(phone);
        message.setFrom("1478981855@qq.com");
        mailSender.send(message);
        return AzResult.ok().data("验证码已发送到您的邮箱");
    }

    @ApiOperation(value = "注册")
    @PostMapping("/apply")
    public AzResult apply(@RequestBody Map<String, String> params) {
        String code = BlogUtils.getCode(request);
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

    @Authorized
    @GetMapping
    @ApiOperation(value = "用户列表",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public PageResult getAdminList(int page, int limit) {
        Page<Admin> list = adminService.getAdminList(page, limit);
        return new PageResult("0", "成功", list.getTotalElements(),
                list.getTotalPages(), list.getContent());
    }

    @Authorized
    @PostMapping
    @ApiOperation(value = "添加用户",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result add(@RequestBody Admin admin) {
        boolean isRepetitive = adminService.adminIsRepetitive(admin.getUsername());
        if (!isRepetitive) {
            adminService.add(admin);
            return new Result("200", "添加成功");
        }
        return new Result("500", "用户名重复");
    }

    @Authorized
    @PutMapping
    @ApiOperation(value = "修改用户",notes = "token需要包含ROOT和ADMIN权限")
    public AzResult update(@RequestBody Map params){
        adminService.update(params);
        return AzResult.ok();
    }

    @Authorized
    @PutMapping("/resetPwd/{id}")
    @ApiOperation(value = "重置密码",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result resetPwd(@PathVariable String id) {
        adminService.resetPwd(id);
        return new Result("200", "密码重置成功");
    }

    @Authorized
    @GetMapping("{id}")
    @ApiOperation(value = "根据id查找用户",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result findById(@PathVariable String id) {
        Admin admin = adminService.findById(id);
        if(admin == null){
            return new Result("500","root用户不可编辑");
        }
        return new Result("200", "查询成功", admin);
    }

    @Authorized
    @PostMapping("/getUserame/{id}")
    @ApiOperation(value = "获取用户名",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result getUsernameById(@PathVariable String id) {
        log.debug(TokenUtils.getUserId(request, rsaKeyConfig));
        String username = adminService.getUsernameById(TokenUtils.getUserId(request, rsaKeyConfig));
        if (username != null) {
            return new Result("200", "已获取到用户名", username);
        }
        return new Result("500", "请登录后操作");
    }

    @GetMapping("/getUsername")
    @ApiOperation(value = "获取token中的用户名")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result getUsernameByToken() {
        return new Result("200", "获取到用户名", TokenUtils.getUsername(request, rsaKeyConfig));
    }

    @GetMapping("/refreshToken")
    @ApiOperation(value = "刷新token")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        JwtUtils.parserToken(token, rsaKeyConfig.getPublicKey());
        return new Result("200", "token未过期");
    }

    @Authorized
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public Result delete(@PathVariable String id) {
        boolean b = adminService.delete(id);
        if (b) {
            return new Result("200", "删除成功");
        }
        return new Result("500", "root用户不可删除");
    }

    @Authorized
    @GetMapping("search/{keyword}")
    @ApiOperation(value = "根据用户名搜索",notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public PageResult searchByUsername(@PathVariable String keyword,int page,int limit){
        Page<Admin> admins = adminService.searchByUsername(keyword, page, limit);
        return new PageResult("0","搜索成功",
                admins.getTotalElements(),admins.getTotalPages(),admins.getContent());
    }
}

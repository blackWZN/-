package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.entity.Admin;
import com.wzn.ablog.admin.service.AdminService;
import com.wzn.ablog.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private AdminService adminService;

    @PostMapping("/apply")
    public Result apply(@RequestBody Admin admin){
        adminService.apply(admin);
        return new Result("200","你的账号申请已提交，审核结果会发送到您的邮箱");
    }

    @PostMapping("/add")
    public String add(@RequestBody Admin admin){
        return "add";
    }

    @DeleteMapping("/del{userId}")
    public String del(@PathVariable("userId") String userId){
        return "del";
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        return null;
    }

    //获取用户名
    @PostMapping("/getUserame/{id}")
    public Result getUsernameById(@PathVariable("id") String id){
        log.debug(id);
        String username = adminService.getUsernameById(id);
        if(username != null){
            return new Result("200","已获取到用户名",username);
        }
        return new Result("500","请登录后操作");
    }

    //根据token获取用户名
    @GetMapping("/getUsername")
    public Result getUsernameByToken(){
        return new Result("200","获取到用户名",getUsername());
    }

    @PostMapping("/loginOut")
    public Result loginOut(){

        return null;
    }
}

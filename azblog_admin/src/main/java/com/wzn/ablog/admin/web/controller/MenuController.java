package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.MenuService;
import com.wzn.ablog.admin.vo.Result;
import com.wzn.ablog.common.entity.Menu;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/muen")
public class MenuController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

    @Autowired
    private MenuService menuService;

    //根据权限加载菜单
    @GetMapping
    public Result list(){
        List<Menu> roleList = menuService.list(TokenUtils.getRoles(request,rsaKeyConfig));
        if(roleList == null){
            return new Result("200","菜单加载失败");
        }
        return new Result("200","菜单加载成功",roleList);
    }
}

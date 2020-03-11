package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.MenuService;
import com.wzn.ablog.common.entity.Menu;
import com.wzn.ablog.common.utils.RsaKeyConfig;
import com.wzn.ablog.common.utils.TokenUtils;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import com.wzn.ablog.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/byRole")
    public Result list(){
        List<Menu> roleList = menuService.listByAuth(TokenUtils.getRoles(request,rsaKeyConfig));
        if(roleList == null){
            return new Result("200","菜单加载失败");
        }
        return new Result("200","菜单加载成功",roleList);
    }

    @GetMapping
    public PageResult list(int page, int limit){
        Page<Menu> pageInfo = menuService.list(page, limit);
        return  new PageResult("0","列表加载成功",pageInfo.getTotalElements()
                ,pageInfo.getTotalPages(),pageInfo.getContent());
    }

    @GetMapping("/{id}")
    public AzResult findbyId(@PathVariable String id){
        Menu Menu = menuService.findById(id);
        return AzResult.ok().data(Menu);
    }

    @DeleteMapping("/{ids}")
    public AzResult del(@PathVariable String[] ids){
        menuService.del(ids);
        return AzResult.ok("删除成功");
    }

    @PostMapping
    public AzResult add(@RequestBody Menu Menu){
        menuService.add(Menu);
        return AzResult.ok("添加成功");
    }

    @PutMapping
    public AzResult update(@RequestBody Menu Menu){
        menuService.update(Menu);
        return AzResult.ok("更新成功");
    }

    //根据菜单名搜索
    @GetMapping("/search/{menuName}")
    public AzResult searchMenu(@PathVariable String menuName){
        List<Menu> menus = menuService.searchMenu(menuName);
        return AzResult.ok().data(menus);
    }
}

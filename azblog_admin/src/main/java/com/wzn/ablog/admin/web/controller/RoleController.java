package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.RoleService;
import com.wzn.ablog.common.entity.Port;
import com.wzn.ablog.common.entity.Role;
import com.wzn.ablog.common.vo.AzResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/roleList")
    public AzResult roleList(){
        List<Role> list = roleService.roleList();
        return  AzResult.ok().data(list);
    }

    @GetMapping("/portList")
    public AzResult portList(){
        List<Port> ports = roleService.portList();
        return  AzResult.ok().data(ports);
    }

    @GetMapping("/{id}")
    public AzResult findbyId(@PathVariable String id){
        Role Role = roleService.findById(id);
        return AzResult.ok().data(Role);
    }

    @DeleteMapping("/{ids}")
    public AzResult del(@PathVariable String[] ids){
        roleService.del(ids);
        return AzResult.ok("删除成功");
    }

    @PostMapping
    public AzResult add(@RequestBody Role Role){
        roleService.add(Role);
        return AzResult.ok("添加成功");
    }

    @PutMapping
    public AzResult update(@RequestBody Role Role){
        roleService.update(Role);
        return AzResult.ok("更新成功");
    }

    @GetMapping("search/{roleName}")
    public AzResult searchRole(@PathVariable String roleName){
        List<Role> roles = roleService.searchRole(roleName);
        return AzResult.ok().data(roles);
    }
}

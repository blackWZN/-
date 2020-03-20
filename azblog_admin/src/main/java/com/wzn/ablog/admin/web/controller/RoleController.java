package com.wzn.ablog.admin.web.controller;

import com.wzn.ablog.admin.service.RoleService;
import com.wzn.ablog.common.annotation.Authorized;
import com.wzn.ablog.common.entity.Port;
import com.wzn.ablog.common.entity.Role;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.ablog.common.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(tags = {"权限"})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Authorized
    @GetMapping("/roleList")
    @ApiOperation(value = "权限列表", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public PageResult roleList(int page, int limit) {
        Page<Role> rolePage = roleService.roleList(page, limit);
        return new PageResult("0", "加载完成", rolePage.getTotalElements(), rolePage.getTotalPages()
                , rolePage.getContent());
    }

    @Authorized
    @GetMapping("/portList")
    @ApiOperation(value = "接口列表", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult portList() {
        List<Port> ports = roleService.portList();
        return AzResult.ok().data(ports);
    }

    @Authorized
    @GetMapping("/{id}")
    @ApiOperation(value = "查找权限", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult findbyId(@PathVariable String id) {
        Role Role = roleService.findById(id);
        return AzResult.ok().data(Role);
    }

    @Authorized
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除权限", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult del(@PathVariable String[] ids) {
        roleService.del(ids);
        return AzResult.ok("删除成功");
    }

    @Authorized
    @PostMapping
    @ApiOperation(value = "添加权限", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult add(@RequestBody Role Role) {
        roleService.add(Role);
        return AzResult.ok("添加成功");
    }

    @Authorized
    @PutMapping
    @ApiOperation(value = "添加权限", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult update(@RequestBody Role Role) {
        roleService.update(Role);
        return AzResult.ok("修改成功");
    }

    @Authorized
    @GetMapping("search/{roleName}")
    @ApiOperation(value = "搜索权限", notes = "token需要包含ROOT和ADMIN权限")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true)
    public AzResult searchRole(@PathVariable String roleName) {
        List<Role> roles = roleService.searchRole(roleName);
        return AzResult.ok().data(roles);
    }
}

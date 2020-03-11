package com.wzn.ablog.admin.service;

import com.wzn.ablog.admin.dao.PortDao;
import com.wzn.ablog.admin.dao.RoleDao;
import com.wzn.ablog.common.entity.Port;
import com.wzn.ablog.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private PortDao portDao;

    @Autowired
    private RoleDao roleDao;

    //查找全部接口
    public List<Port> portList(){
        List<Port> list = portDao.findAll();
        return list;
    }

    //查找全部权限
    public List<Role> roleList(){
        List<Role> list = roleDao.findAll();
        return list;
    }

    //根据id查找权限
    public Role findById(String id){
        Role role = roleDao.findById(id).get();
        return role;
    }

    //添加
    public void add(Role role){
        roleDao.save(role);
    }

    //更新
    public void update(Role role){
        roleDao.save(role);
    }

    //根据id删除
    public void del(String[] ids){
        for(String id : ids){
            roleDao.deleteById(id);
        }
    }

    //根据权限名搜索
    public List<Role> searchRole(String roleName){
        List<Role> roles = roleDao.findByRoleNameLink(roleName);
        return roles;
    }
}

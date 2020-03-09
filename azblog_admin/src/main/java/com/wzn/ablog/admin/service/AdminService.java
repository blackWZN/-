package com.wzn.ablog.admin.service;

import com.wzn.ablog.admin.dao.AdminDao;
import com.wzn.ablog.common.entity.Admin;
import com.wzn.ablog.common.entity.Role;
import com.wzn.ablog.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IdWorker idWorker;

    //登录
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminDao.findAdminByUsername(s);
        return admin;
    }

    //申请账号
    public void apply(Map<String,String> params) {
        Admin admin = new Admin();
        Stream.of(params).forEach(map->{
            admin.setId(idWorker.nextId()+"");
            admin.setUsername(map.get("username"));
            admin.setPassword(encoder.encode(map.get("password")));
            admin.setSex("男");
            admin.setEmail(map.get("email"));
            admin.setStatus("1");
        });
        adminDao.save(admin);
    }

    //根据id查找用户名
    public String getUsernameById(String id) {
        return adminDao.findUsernameById(id);
    }

    //加载用户列表
    public Page<Admin> getAdminList(int page, int limit) {
        Page<Admin> pageInfo = adminDao.findAll(PageRequest.of(page - 1, limit, Sort.by("createTime").descending()));
        return pageInfo;
    }

    //重置密码
    public void resetPwd(String id) {
        String encode = encoder.encode("123456");
        adminDao.resetPwdById(id, encode);
    }

    //根据id查找用户
    public Admin findById(String id) {
        return adminDao.findById(id).get();
    }

    //添加用户并设置权限
    public void add(Admin admin) {
        admin.setId(String.valueOf(idWorker.nextId()));
        admin.setStatus(admin.getStatus() != null ?
                admin.getStatus().equals("on") ? "0" : "1":"1");
        admin.setPassword(encoder.encode(admin.getPassword()));
        Admin save = adminDao.save(admin);
        String[] reqRoles = admin.getResRoles();
        for (String role : reqRoles) {
            String roleName = adminDao.findRoleIdByRoleName(role);
            adminDao.setRoles(String.valueOf(idWorker.nextId()), save.getId(), roleName);
        }
    }

    //用户名是否重复
    public boolean adminIsRepetitive(String username){
        String name = adminDao.findUsername(username);
        if(name != null){
            return true;
        }
        return false;
    }

    public boolean delete(String id){
        Admin admin = adminDao.findById(id).get();
        adminDao.deleteRolesByAdminId(id);
        if(admin.getUsername().equals("root")){
            return false;
        }
        adminDao.deleteById(id);
        return true;
    }
}

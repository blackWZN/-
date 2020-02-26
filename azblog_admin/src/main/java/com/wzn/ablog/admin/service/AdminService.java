package com.wzn.ablog.admin.service;

import com.wzn.ablog.admin.dao.AdminDao;
import com.wzn.ablog.common.entity.Admin;
import com.wzn.ablog.common.entity.Role;
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

@Slf4j
@Service
@Transactional
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminDao.findAdminByUsername(s);

        return admin;
    }

    //申请账号
    public void apply(Admin admin) {
        adminDao.save(admin);
    }

    public String getUsernameById(String id) {
        return adminDao.findNicknameById(id);
    }

    public Page<Admin> getAdminList(int page, int limit) {
        Page<Admin> pageInfo = adminDao.findAll(PageRequest.of(page - 1, limit, Sort.by("createTime").descending()));
        return pageInfo;
    }

    public void resetPwd(String id) {
        String encode = encoder.encode("123456");
        adminDao.resetPwdById(id, encode);
    }
}

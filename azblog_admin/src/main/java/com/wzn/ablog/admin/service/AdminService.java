package com.wzn.ablog.admin.service;

import com.wzn.ablog.admin.dao.AdminDao;
import com.wzn.ablog.admin.dao.RoleDao;
import com.wzn.ablog.admin.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminDao.findAdminByUsername(s);
//        List<Role> roles = roleDao.findRolesByAminId(admin.getId());
//        admin.setRoles(roles);
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        for (Role permission : roles) {
//            if (permission != null && permission.getRole_name() !=null) {
//                admin.getRoles().add(permission);
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getRole_name());
//                //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象
//                grantedAuthorities.add(grantedAuthority);
//            }
//        }
//        return new User(admin.getUsername(), admin.getPassword(),grantedAuthorities);
        return admin;
    }

    //申请账号
    public void apply(Admin admin) {
        adminDao.save(admin);
    }

    public String getUsernameById(String id){
        return adminDao.findNicknameById(id);
    }


}

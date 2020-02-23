package com.wzn.ablog.admin.service;

import com.wzn.ablog.admin.dao.MenuDao;
import com.wzn.ablog.admin.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<Menu> list(String roles){
        String[] roleName = roles.split(",");
        List<Menu> roleList = menuDao.findByRoles(roles);
        for (int i = 0; i < roleName.length && roleName.length>1 ; i++) {
            roleList.addAll(menuDao.findByRoles(roleName[i]));
        }

        return roleList;
    }
}

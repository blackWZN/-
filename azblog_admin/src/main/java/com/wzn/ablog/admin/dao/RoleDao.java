package com.wzn.ablog.admin.dao;

import com.wzn.ablog.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {

    /*  根据用户id查找用户权限
        nativeQuery : 使用sql的方式查询
     */
    @Query(value = "SELECT * FROM sys_role WHERE role_id \n" +
            "IN(SELECT role_id FROM sys_admin_role WHERE admin_id = :adminId)",nativeQuery=true)
    List<Role> findRolesByAminId(@Param("adminId") String adminId);


    /*  根据PortId查找请求URL权限
        nativeQuery : 使用sql的方式查询
     */
    @Query(value = "SELECT * FROM sys_role WHERE role_id \n" +
            "IN(SELECT role_id FROM sys_port_role WHERE port_id = :portId)",nativeQuery=true)
    List<Role> findRolesByPortId(@Param("portId") String portId);
}

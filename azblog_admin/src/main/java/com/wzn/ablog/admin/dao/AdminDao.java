package com.wzn.ablog.admin.dao;


import com.wzn.ablog.common.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    @Query(value = "SELECT * FROM admin WHERE username = :username " +
            "OR email = :username AND STATUS = '1'", nativeQuery = true)
    Admin findAdminByUsername(String username);

    @Query(value = "SELECT username FROM admin WHERE id = :id", nativeQuery = true)
    String findUsernameById(@Param("id") String id);

    @Query(value = "UPDATE admin SET PASSWORD = :encode WHERE id = :id", nativeQuery = true)
    @Modifying
    void resetPwdById(@Param("id") String id, @Param("encode") String encode);

    @Query(value = "SELECT role_id FROM sys_role WHERE role_name = :roleName", nativeQuery = true)
    String findRoleIdByRoleName(String roleName);

    @Query(value = "INSERT INTO sys_admin_role SET id=:id,admin_id = :adminId,role_id = :roleId",
            nativeQuery = true)
    @Modifying
    void setRoles(@Param("id") String id, @Param("adminId") String adminId,
                  @Param("roleId") String roleId);

    @Query(value = "SELECT username FROM admin WHERE username = :username", nativeQuery = true)
    String findUsername(String username);

    @Query(value = "DELETE FROM sys_admin_role WHERE admin_id = :adminId",nativeQuery = true)
    @Modifying
    void deleteRolesByAdminId(String adminId);
}

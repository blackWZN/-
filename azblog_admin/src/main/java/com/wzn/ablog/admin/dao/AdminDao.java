package com.wzn.ablog.admin.dao;


import com.wzn.ablog.common.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    @Query(value = "SELECT * FROM admin WHERE username = :username " +
            "OR email = :username AND STATUS = '1'",nativeQuery = true)
    Admin findAdminByUsername(String username);

    @Query(value = "SELECT username FROM admin WHERE id = :id", nativeQuery = true)
    String findNicknameById(@Param("id") String id);

    @Query(value = "UPDATE admin SET PASSWORD = :encode WHERE id = :id",nativeQuery = true)
    @Modifying
    void resetPwdById(@Param("id") String id,@Param("encode") String encode);
}

package com.wzn.ablog.admin.dao;


import com.wzn.ablog.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    Admin findAdminByUsername(String username);

    @Query(value = "SELECT nickname FROM admin WHERE id = :id", nativeQuery = true)
    String findNicknameById(@Param("id") String id);
}

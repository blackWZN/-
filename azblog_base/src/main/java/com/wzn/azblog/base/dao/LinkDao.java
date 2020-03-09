package com.wzn.azblog.base.dao;

import com.wzn.ablog.common.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkDao extends JpaRepository<Link,String>, JpaSpecificationExecutor<Link> {

    @Query(value = "SELECT * FROM link WHERE title LIKE CONCAT('%',:keywords,'%')",nativeQuery = true)
    List<Link> search(@Param("keywords") String keywords);
}

package com.wzn.azblog.base.dao;

import com.wzn.ablog.common.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LinkDao extends JpaRepository<Link,String>, JpaSpecificationExecutor<Link> {
}

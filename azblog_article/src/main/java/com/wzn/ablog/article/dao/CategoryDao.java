package com.wzn.ablog.article.dao;

import com.wzn.ablog.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CategoryDao extends JpaRepository<Category,String>, JpaSpecificationExecutor<Category> {

    @Query(value = "SELECT NAME FROM `category` WHERE id=:id",nativeQuery = true)
    String findNameById(String id);
}

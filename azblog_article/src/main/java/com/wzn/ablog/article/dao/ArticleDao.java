package com.wzn.ablog.article.dao;

import com.wzn.ablog.common.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleDao extends JpaRepository<Article,String>, JpaSpecificationExecutor<Article> {

}

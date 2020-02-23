package com.wzn.ablog.article.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    private String id;
    private String user_id;
    private String article_id;
    private String user_name;
    private String content;
    private String status;
    private String avatar;
    private Date create_time;
    private Date update_time;
}

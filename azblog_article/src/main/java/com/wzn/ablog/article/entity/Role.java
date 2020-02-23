package com.wzn.ablog.article.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
public class Role {
    @Id
    private String role_id;
    private String role_name;
    private String comments;
    private String create_time;
    private String update_time;


}

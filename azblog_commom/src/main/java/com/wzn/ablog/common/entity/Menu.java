package com.wzn.ablog.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_menu")
public class Menu{
    @Id
    private String id;
    private String parentId;
    private String menuName;
    private String menuIcon;
    @Column(name = "create_time")
    private Date createTime;
    private Date updateTime;
    private String roles;
}

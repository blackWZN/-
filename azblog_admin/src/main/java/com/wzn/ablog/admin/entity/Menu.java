package com.wzn.ablog.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_menu")
public class Menu{
    @Id
    private String id;
    private String parentid;
    private String menuname;
    private String menuicon;
    @Column(name = "create_time")
    private Date createTime;
    private Date updatetime;
    private String roles;
}

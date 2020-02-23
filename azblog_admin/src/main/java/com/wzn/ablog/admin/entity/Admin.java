package com.wzn.ablog.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.catalina.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "admin")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements UserDetails{
    @Id
    private String id;
    private String nickname;
    private String username;
    private String password;
    private String avatar;
    private String sex;
    private String birthday;
    private String email;
    private String create_time;
    private String last_login_time;
    //账号状态 0 正常 1 锁定
    private String state;



    /**
     * fetch=FetchType.EAGER 关闭懒加载
     */
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_admin_role",//中间表的名称
            //中间表admin_id字段关联sys_role表的主键字段id
            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "id")},
            //中间表role_id的字段关联sys_user表的主键role_id
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
    )
    private List<Role> roles = new ArrayList<>();



    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthority = new ArrayList<> ();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                grantedAuthority.add(new SimpleGrantedAuthority(role.getRole_name()));
            }
        }
        return grantedAuthority;

    }



    //账号未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号未被锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return state.equals("1") ? false : true;
    }

    //密码未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账号是被删除
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

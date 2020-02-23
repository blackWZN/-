package com.wzn.ablog.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
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

    private List<Role> roles = new ArrayList<>();

}

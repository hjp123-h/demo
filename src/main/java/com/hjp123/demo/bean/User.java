package com.hjp123.demo.bean;

import lombok.Data;

/**
 * 用户注册信息类
 */
@Data
public class User {

    private Long id;
    private String name;
    private String account;
    private String password;
    private String token;

    public User() {
    }

    public User(String name, String account, String password, String token) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.token = token;
    }

    public User(Long id, String name, String account, String password, String token) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.token = token;
    }

    public User(String name, String account, String password) {
        this.name = name;
        this.account = account;
        this.password = password;
    }

    public
    User(Long id, String name, String account, String password) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
    }
}

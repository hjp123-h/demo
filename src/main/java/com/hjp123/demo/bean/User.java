package com.hjp123.demo.bean;

public class User {

    private Long id;
    private String name;
    private String account;
    private String password;

    public User() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

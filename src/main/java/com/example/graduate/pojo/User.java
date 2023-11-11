package com.example.graduate.pojo;

import java.util.Date;

public class User {
    private Long id;            //ID
    private String account;     //账号
    private String password;    //密码
    private String nickname;    //昵称
    private String email;       //邮箱
    private String photo;       //头像
    private String phone;       //电话
    private Date bothDay;       //生日
    private boolean status;     //账户存活状态

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(Long id, String account, String password, String nickname, String email, String photo, String phone, Date bothDay, boolean status) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.photo = photo;
        this.phone = phone;
        this.bothDay = bothDay;
        this.status = status;
    }

    public User() {
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBothDay() {
        return bothDay;
    }

    public void setBothDay(Date bothDay) {
        this.bothDay = bothDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", bothDay=" + bothDay +
                ", status=" + status +
                '}';
    }
}

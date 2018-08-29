package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 管理人员信息
 */
public class AdminInfo implements Serializable {

    private int userId;

    private String username;

    private String realname;

    private String password;

    private String phonenumber;

    private int userGroupId;

    private int authorityId;

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() { return phonenumber; }

    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() { return realname; }

    public void setRealname(String realname) { this.realname = realname; }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }



    @Override
    public String toString() {
        return "AdminInfo{" +
                "userId=" + userId +
                ", realname='" + realname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", userGroupId=" + userGroupId +
                '}';
    }
}

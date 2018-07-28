package com.dpk.wgj.bean;

import java.io.Serializable;

public class AdminGroupAuthority implements Serializable {

    private int adminGroupId;

    private String adminGroupName;

    private String content;

    private int adminId;

    public AdminGroupAuthority() {
    }

    public int getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(int adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    public String getAdminGroupName() {
        return adminGroupName;
    }

    public void setAdminGroupName(String adminGroupName) {
        this.adminGroupName = adminGroupName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}

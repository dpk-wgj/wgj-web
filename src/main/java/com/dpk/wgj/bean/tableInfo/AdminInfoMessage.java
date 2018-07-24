package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.AdminInfo;

public class AdminInfoMessage extends TableMessage {
    private AdminInfo adminInfo = new AdminInfo();

    private String username;

    public AdminInfo getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

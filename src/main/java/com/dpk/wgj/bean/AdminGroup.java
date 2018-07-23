package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by hlx on 2018/7/22.
 * 管理人员分组信息
 */
public class AdminGroup implements Serializable {

    private int adminGroupId;

    private String groupName;

    private String permission;

    public int getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(int adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "AdminGroup{" +
                "adminGroupId=" + adminGroupId +
                ", groupName='" + groupName + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}

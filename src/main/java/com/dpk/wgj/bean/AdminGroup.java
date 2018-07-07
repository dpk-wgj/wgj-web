package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 管理人员分组信息
 */
public class AdminGroup implements Serializable {

    private int user_group_id;

    private String groupName;

    private String permission;

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
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
                "user_group_id=" + user_group_id +
                ", groupName='" + groupName + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}

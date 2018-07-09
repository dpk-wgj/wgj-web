package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 管理人员分组信息
 */
public class UserGroup implements Serializable {

    private int userGroupId;

    private String groupName;

    private String permission;

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
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
        return "UserGroup{" +
                "userGroupId=" + userGroupId +
                ", groupName='" + groupName + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}

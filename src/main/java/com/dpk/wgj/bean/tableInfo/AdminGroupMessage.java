package com.dpk.wgj.bean.tableInfo;

import com.dpk.wgj.bean.AdminGroup;


public class AdminGroupMessage extends TableMessage {

    private AdminGroup adminGroup = new AdminGroup();

    private String groupName;

    public AdminGroup getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(AdminGroup adminGroup) {
        this.adminGroup = adminGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

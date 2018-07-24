package com.dpk.wgj.bean.DTO;

import com.dpk.wgj.bean.AdminGroup;
import com.dpk.wgj.bean.AdminInfo;

public class AdminInfoDTO {
    private AdminInfo adminInfo;

    private AdminGroup adminGroup;

    public AdminInfoDTO(){}

    public AdminInfoDTO(AdminInfo adminInfo,AdminGroup adminGroup){
        this.adminInfo = adminInfo;
        this.adminGroup = adminGroup;
    }

    public AdminInfo getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }

    public AdminGroup getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(AdminGroup adminGroup) {
        this.adminGroup = adminGroup;
    }
}

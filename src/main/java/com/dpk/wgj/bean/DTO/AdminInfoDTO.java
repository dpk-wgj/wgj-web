package com.dpk.wgj.bean.DTO;

import com.dpk.wgj.bean.AdminGroup;
import com.dpk.wgj.bean.AdminGroupAuthority;
import com.dpk.wgj.bean.AdminInfo;

public class AdminInfoDTO {
    private AdminInfo adminInfo;

    private AdminGroup adminGroup;

    private AdminGroupAuthority adminGroupAuthority;

    public AdminInfoDTO(){}

    public AdminInfoDTO(AdminInfo adminInfo,AdminGroup adminGroup,AdminGroupAuthority adminGroupAuthority){
        this.adminInfo = adminInfo;
        this.adminGroup = adminGroup;
        this.adminGroupAuthority = adminGroupAuthority;
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

    public AdminGroupAuthority getAdminGroupAuthority() {
        return adminGroupAuthority;
    }

    public void setAdminGroupAuthority(AdminGroupAuthority adminGroupAuthority) {
        this.adminGroupAuthority = adminGroupAuthority;
    }
}

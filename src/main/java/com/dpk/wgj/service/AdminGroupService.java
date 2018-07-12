package com.dpk.wgj.service;

import com.dpk.wgj.bean.AdminGroup;

import java.util.List;

/*
qinghua
 */
public interface AdminGroupService {
    public AdminGroup getAdminGroupByName(String groupName) throws Exception;

    public List<AdminGroup> getAllAdminGroup() throws Exception;

    public AdminGroup getAdminGroupById(int adminGroupId) throws Exception;

    public int addAdminGroup(AdminGroup adminGroup) throws Exception;

    public int updateAdminGroupById(AdminGroup adminGroup) throws Exception;

    public int deleteAdminGroup(int adminGroup) throws Exception;
}
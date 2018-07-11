package com.dpk.wgj.service;

import com.dpk.wgj.bean.UserGroup;

import java.util.List;
/*
qinghua
 */
public interface UserGroupService {
    public UserGroup getUserGroupByName(String groupName) throws Exception;

    public List<UserGroup> getAllUserGroup() throws Exception;

    public UserGroup getUserGroupById(int userGroupId) throws Exception;

    public int addUserGroup(UserGroup userGroup) throws Exception;

    public int updateUserGroupById(UserGroup userGroup) throws Exception;

    public int deleteUserGroup(int userGroupId) throws Exception;
}
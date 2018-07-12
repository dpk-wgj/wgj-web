package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.UserGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
qinghua
 */
@Mapper
public interface UserGroupMapper {

    public UserGroup getUserGroupByName(String groupName) throws Exception;

    public List<UserGroup> getAllUserGroup() throws Exception;

    public UserGroup getByUserId(int userGroupId) throws Exception;

    public int addUserGroup(UserGroup userGroup) throws Exception;

    public int updateUserGroupById(UserGroup userGroup) throws Exception;

    public int deleteUserGroup(int userGroupId) throws Exception;

}

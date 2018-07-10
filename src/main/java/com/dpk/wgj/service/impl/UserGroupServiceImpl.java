package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.UserGroup;
import com.dpk.wgj.mapper.UserGroupMapper;
import com.dpk.wgj.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
qinghua
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    private final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    @Override
    public UserGroup getUserGroupByName(String groupName) {

        UserGroup userGroup;

        try {
            userGroup =  userGroupMapper.getUserGroupByName(groupName);
            return userGroup;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;

    }

    @Override
    public List<UserGroup> getAllUserGroup() {

        List<UserGroup> userGroup;

        try {
            userGroup = userGroupMapper.getAllUserGroup();
            return userGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserGroup getUserGroupById(int userGroupId) {

        UserGroup userGroup;

        try {
            userGroup =  userGroupMapper.getUserGroupById(userGroupId);
            return userGroup;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;
    }

    @Override
    public int addUserGroup(UserGroup userGroup) {

        int addStatus = 0;

        try {
            addStatus = userGroupMapper.addUserGroup(userGroup);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addStatus;
    }

    @Override
    public int updateUserGroupById(UserGroup userGroup) {

        int upStatus = 0;

        try {
            upStatus = userGroupMapper.updateUserGroupById(userGroup);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return upStatus;
    }

    @Override
    public int deleteUserGroup(int userGroupId) {

        int delStatus = 0;

        try {
            delStatus = userGroupMapper.deleteUserGroup(userGroupId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delStatus;
    }
}

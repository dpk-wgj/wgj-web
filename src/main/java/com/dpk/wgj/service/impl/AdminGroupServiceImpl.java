package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.AdminGroup;
import com.dpk.wgj.mapper.AdminGroupMapper;
import com.dpk.wgj.service.AdminGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
qinghua
 */
@Service
public class AdminGroupServiceImpl implements AdminGroupService {

    @Autowired
    private AdminGroupMapper adminGroupMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminGroupServiceImpl.class);

    @Override
    public AdminGroup getAdminGroupByName(String groupName) {

        AdminGroup adminGroup;

        try {
            adminGroup =  adminGroupMapper.getAdminGroupByName(groupName);
            return adminGroup;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;

    }

    @Override
    public List<AdminGroup> getAllAdminGroup() {

        List<AdminGroup> adminGroup;

        try {
            adminGroup = adminGroupMapper.getAllAdminGroup();
            return adminGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AdminGroup getAdminGroupById(int adminGroupId) {

        AdminGroup adminGroup;

        try {
            adminGroup =  adminGroupMapper.getAdminGroupById(adminGroupId);
            return adminGroup;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;
    }

    @Override
    public int addAdminGroup(AdminGroup adminGroup) {

        int addStatus = 0;

        try {
            addStatus = adminGroupMapper.addAdminGroup(adminGroup);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addStatus;
    }

    @Override
    public int updateAdminGroupById(AdminGroup adminGroup) {

        int upStatus = 0;

        try {
            upStatus = adminGroupMapper.updateAdminGroupById(adminGroup);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return upStatus;
    }

    @Override
    public int deleteAdminGroup(int adminGroup) {

        int delStatus = 0;

        try {
            delStatus = adminGroupMapper.deleteAdminGroup(adminGroup);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delStatus;
    }
}

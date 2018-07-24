package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.tableInfo.AdminInfoMessage;
import com.dpk.wgj.mapper.AdminInfoMapper;
import com.dpk.wgj.service.AdminInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminInfoServiceImpl.class);

    @Override
    public AdminInfo getAdminByUsername(String username) {

        AdminInfo adminInfo;

        try {
            adminInfo =  adminInfoMapper.getAdminByUsername(username);
            return adminInfo;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;

    }

    @Override
    public List<AdminInfo> getAllAdminInfo(AdminInfoMessage adminInfoMessage) {

        List<AdminInfo> adminInfos;

        try {
            adminInfos = adminInfoMapper.getAllAdminInfo(adminInfoMessage);
            return adminInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
@Override
public int getAllAdminInfoCount(AdminInfoMessage adminInfoMessage) {
        int count  =0;
        try{
            count = adminInfoMapper.getAllAdminInfoCount(adminInfoMessage);
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
}

    @Override
    public AdminInfo getUserInfoById(int userId) {

        AdminInfo adminInfo;

        try {
            adminInfo =  adminInfoMapper.getUserInfoById(userId);
            return adminInfo;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return null;
    }

    @Override
    public int addAdminInfo(AdminInfo adminInfo) {

        int addStatus = 0;

        try {
            addStatus = adminInfoMapper.addAdminInfo(adminInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addStatus;
    }

    @Override
    public int updateAdminInfo(AdminInfo adminInfo) {

        int upStatus = 0;

        try {
            upStatus = adminInfoMapper.updateAdminInfo(adminInfo);
            return upStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return upStatus;
    }

    @Override
    public int deleteAdminInfoById(int userId) {

        int delStatus = 0;

        try {
            delStatus = adminInfoMapper.deleteAdminInfoById(userId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delStatus;
    }
}

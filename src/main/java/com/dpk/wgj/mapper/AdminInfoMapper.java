package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.AdminInfo;
import com.dpk.wgj.bean.tableInfo.AdminInfoMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminInfoMapper {

    public AdminInfo getAdminByUsername(String username) throws Exception;

    public List<AdminInfo> getAllAdminInfo(AdminInfoMessage adminInfoMessage) throws Exception;

    public int  getAllAdminInfoCount(AdminInfoMessage adminInfoMessage) throws Exception;

    public AdminInfo getUserInfoById(int userId) throws Exception;

    public int addAdminInfo(AdminInfo adminInfo) throws Exception;

    public int updateAdminInfo(AdminInfo adminInfo) throws Exception;

    public int deleteAdminInfoById(int userId) throws Exception;

}

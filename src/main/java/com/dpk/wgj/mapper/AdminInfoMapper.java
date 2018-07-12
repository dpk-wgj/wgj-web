package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.AdminInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminInfoMapper {

    public AdminInfo getAdminByUsername(String username) throws Exception;

    public List<AdminInfo> getAllAdminByUsername(String username) throws Exception;

    public List<AdminInfo> getAllAdminInfo() throws Exception;

    public AdminInfo getUserInfoById(int userId) throws Exception;

    public int addAdminInfo(AdminInfo adminInfo) throws Exception;

    public int updateAdminInfo(AdminInfo adminInfo) throws Exception;

    public int deleteAdminInfoById(int userId) throws Exception;

}

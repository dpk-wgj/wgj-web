package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.AdminGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
qinghua
 */
@Mapper
public interface AdminGroupMapper {

    public AdminGroup getAdminGroupByName(String groupName) throws Exception;

    public List<AdminGroup> getAllAdminGroup() throws Exception;

    public AdminGroup getAdminGroupById(int adminGroupId) throws Exception;

    public int addAdminGroup(AdminGroup adminGroup) throws Exception;

    public int updateAdminGroupById(AdminGroup adminGroup) throws Exception;

    public int deleteAdminGroup(int adminGroup) throws Exception;

}

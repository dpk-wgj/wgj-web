package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.UserGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper {

    public UserGroup getByUserId(Integer userGroupId) throws Exception;

}

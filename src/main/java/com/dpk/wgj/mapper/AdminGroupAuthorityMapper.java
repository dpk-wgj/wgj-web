package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.AdminGroupAuthority;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AdminGroupAuthorityMapper {

    public AdminGroupAuthority getAdminGroupAuthorityById(int adminGroupId) throws Exception;

}

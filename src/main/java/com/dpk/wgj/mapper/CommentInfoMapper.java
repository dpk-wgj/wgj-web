package com.dpk.wgj.mapper;

import com.dpk.wgj.bean.CommentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentInfoMapper {

    public int addCommentInfoByOrderId(CommentInfo commentInfo) throws Exception;

    public int deleteCommentInfoByCommentId(int commentId) throws Exception;

    public CommentInfo getCommendInfoByOrderId(int orderId) throws Exception;

}

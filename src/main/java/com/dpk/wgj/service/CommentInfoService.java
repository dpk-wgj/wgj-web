package com.dpk.wgj.service;

import com.dpk.wgj.bean.CommentInfo;

public interface CommentInfoService {

    public int addCommentInfoByOrderId(CommentInfo commentInfo) throws Exception;

    public int deleteCommentInfoByCommentId(int commentId) throws Exception;

    public CommentInfo getCommendInfoByOrderId(int orderId) throws Exception;

}

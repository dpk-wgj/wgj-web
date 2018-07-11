package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.CommentInfo;
import com.dpk.wgj.service.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhoulin on 2018/7/11.
 */
@Service
public class CommentInfoSeriveImpl implements CommentInfoService {

    @Autowired
    private CommentInfoService commentInfoService;

    @Override
    public int addCommentInfoByOrderId(CommentInfo commentInfo) {

        int addStatus = 0;

        try {
            addStatus = commentInfoService.addCommentInfoByOrderId(commentInfo);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteCommentInfoByCommentId(int commentId) {

        int delStatus = 0;

        try {
            delStatus = commentInfoService.deleteCommentInfoByCommentId(commentId);
            return delStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public CommentInfo getCommendInfoByOrderId(int orderId) {

        CommentInfo commentInfo;

        try {
            commentInfo = commentInfoService.getCommendInfoByOrderId(orderId);
            return commentInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

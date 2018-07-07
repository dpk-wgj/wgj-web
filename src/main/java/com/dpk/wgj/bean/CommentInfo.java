package com.dpk.wgj.bean;

import java.io.Serializable;

/**
 * Created by zhoulin on 2018/7/7.
 * 订单-评价信息
 */
public class CommentInfo implements Serializable {

    private int commentId;

    private String commentContent;

    private int isClear;

    private int isStable;

    private int isKnow;

    private int isGood;

    private int orderId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getIsClear() {
        return isClear;
    }

    public void setIsClear(int isClear) {
        this.isClear = isClear;
    }

    public int getIsStable() {
        return isStable;
    }

    public void setIsStable(int isStable) {
        this.isStable = isStable;
    }

    public int getIsKnow() {
        return isKnow;
    }

    public void setIsKnow(int isKnow) {
        this.isKnow = isKnow;
    }

    public int getIsGood() {
        return isGood;
    }

    public void setIsGood(int isGood) {
        this.isGood = isGood;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", isClear=" + isClear +
                ", isStable=" + isStable +
                ", isKnow=" + isKnow +
                ", isGood=" + isGood +
                ", orderId=" + orderId +
                '}';
    }
}

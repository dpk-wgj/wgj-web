package com.dpk.wgj.service;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.SmsInfo;

public interface SmsService {

    public int sendMsg(SmsInfo smsInfo) throws Exception;

}

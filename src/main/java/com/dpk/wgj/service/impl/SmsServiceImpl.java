package com.dpk.wgj.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.dpk.wgj.bean.SmsInfo;
import com.dpk.wgj.service.SmsService;
import com.dpk.wgj.utils.AliyunMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int sendMsg(SmsInfo smsInfo) {

        // 从缓存中获取列表
        String key = "" + smsInfo.getUserId();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();


        String phoneNumber = smsInfo.getPhoneNumber();
        String randomNum = smsInfo.getRandomNum();
        String jsonContent = "{\"code\":\"" + randomNum + "\"}";

        // 验证码有效时间
        operations.set(key, randomNum, 5, TimeUnit.MINUTES);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("msgSign", "周林");
        paramMap.put("templateCode", "SMS_139830310");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return 1;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package com.dpk.wgj.utils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsTest {

    public String sendMsg() throws Exception {
        String phoneNumber = "18875856197";
        String randomNum = createRandomNum(6);
        String jsonContent = "{\"code\":\"" + 5201314 + "\"}";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("msgSign", "周林");
        paramMap.put("templateCode", "SMS_139830310");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
        if(!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if(sendSmsResponse.getCode() == null) {
                //这里可以抛出自定义异常
                return "sendSmsResponse.getCode() == null";
            }
            if(!sendSmsResponse.getCode().equals("OK")) {
                //这里可以抛出自定义异常
                return sendSmsResponse.getCode();
            }
        }
        return "成功";
    }

    /**
     * 生成随机数
     * @param num 位数
     * @return
     */
    public static String createRandomNum(int num){
        String randomNumStr = "";
        for(int i = 0; i < num;i ++){
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

}

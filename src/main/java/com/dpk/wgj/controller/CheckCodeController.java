package com.dpk.wgj.controller;

import com.dpk.wgj.bean.Message;
import com.dpk.wgj.bean.SmsInfo;
import com.dpk.wgj.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 乘客端 >> 验证码校验
 */
@RestController
@RequestMapping(value = "/api/driver")
public class CheckCodeController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param smsInfo
     * @return
     */
    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
    public Message checkCodeDriver(@RequestBody SmsInfo smsInfo){

        String key = "" + smsInfo.getUserId();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        String code = operations.get(key);

        if (code.equals(smsInfo.getRandomNum())){
            // 执行更新操作 && 更新成功进行回调

        }

        return new Message(Message.ERROR, "验证码校验错误", "请重新请求");

    }

}

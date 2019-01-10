package com.clt.api.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName : HelloSender
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009 上午 11:18
 * @Description :
 **/
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate template;

    public void send(String str) {
        template.convertAndSend("zqstestqueue", str);
    }
}

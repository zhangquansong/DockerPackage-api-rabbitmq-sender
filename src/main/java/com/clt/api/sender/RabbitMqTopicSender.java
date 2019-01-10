package com.clt.api.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName : RabbitMqTopicSender
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009 上午 11:38
 * @Description :消息发送类（Topic模式）
 **/
@Slf4j
public class RabbitMqTopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object obj) {
        log.info("开始发送消息········");
        synchronized (obj) {
            rabbitTemplate.convertAndSend("amq.topic", "chang-queue", obj);
        }
    }
}

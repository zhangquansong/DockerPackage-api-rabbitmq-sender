package com.clt.api.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
  *@ClassName : FanoutMqSender
  *@Author : zhangquansong
  *@Date : 2019/1/9 0009 上午 11:39
  *@Description :fanout模式发送消息(即发布订阅模式)
  **/
@Slf4j
public class FanoutMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object obj) {
        log.info("发送fanout消息：{}", obj);
        rabbitTemplate.convertAndSend("amq.fanout", "amq.fanout.message", obj);
    }
}

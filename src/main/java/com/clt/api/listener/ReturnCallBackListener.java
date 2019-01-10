package com.clt.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Service;

/**
 * @ClassName : ReturnCallBackListener
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009 上午 11:38
 * @Description :消息发送失败返回监听器
 **/
@Slf4j
@Service
public class ReturnCallBackListener implements ReturnCallback {

    /**
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     * @return void
     * @Author zhangquansong
     * @Date 2019/1/9 0009 上午 11:38
     * @Description :  启动消息失败返回，比如路由不到队列时触发回调
     **/
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息返回处理中...");
        log.info("消息主体 message : " + message);
        log.info("消息主体 message : " + replyCode);
        log.info("描述：" + replyText);
        log.info("消息使用的交换器 exchange : " + exchange);
        log.info("消息使用的路由键 routing : " + routingKey);

    }
}
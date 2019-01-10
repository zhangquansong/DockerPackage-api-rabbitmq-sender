package com.clt.api.sender;

import com.alibaba.fastjson.JSON;
import com.clt.api.utils.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName : RabbitAckSender
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009 上午 11:38
 * @Description :消息回调
 **/
@Slf4j
@Configuration
public class RabbitAckSender implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.appid}")
    private String appId;

    /**
     * @param obj
     * @return void
     * @Author zhangquansong
     * @Date 2019/1/9 0009 上午 11:37
     * @Description :  发送注册成功ACK消息
     **/
    public void sendMqSMS(Object obj) {
        if (Optional.of(obj).isPresent()) {
            String msg = JSON.toJSONString(obj);
            log.info("发送ACK消息成功:{}", msg);
            rabbitTemplate.setConfirmCallback(this);
            CorrelationData cld = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitMqConstants.CLT_QUEUE_SMS, msg, (message) -> {
                message.getMessageProperties().setAppId(appId);
                message.getMessageProperties().setTimestamp(new Date());
                message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                return message;
            }, cld);
        }
    }

    /**
     * @param obj
     * @return void
     * @Author zhangquansong
     * @Date 2019/1/9 0009 上午 11:37
     * @Description :  发送活动奖励ACK消息
     **/
    public void sendReward(Object obj) {
        if (Optional.of(obj).isPresent()) {
            String msg = JSON.toJSONString(obj);
            log.info("发送活动奖励ACK消息:{}", msg);
            rabbitTemplate.setConfirmCallback(this);
            CorrelationData cld = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitMqConstants.CLT_QUEUE_REWARD, msg, (message) -> {
                message.getMessageProperties().setAppId(appId);
                message.getMessageProperties().setTimestamp(new Date());
                message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                return message;
            }, cld);
        }
    }


    /**
     * @param cld
     * @param ack
     * @param cause
     * @return void
     * @Author zhangquansong
     * @Date 2019/1/9 0009 上午 11:35
     * @Description : 接收确认消息(只确认生产者消息发送成功，消费者是否处理成功不做保证)
     **/
    @Override
    public void confirm(CorrelationData cld, boolean ack, String cause) {
        if (ack) {
            if (null == cld) {
                log.debug("消息发送成功!");
                return;
            }
            log.debug("消息发送成功[id]:{}", cld.getId());
        } else {
            log.debug("消息发送失败[id]:,{},[cause]:{}", new Object[]{cld.getId(), cause});
        }
    }
}

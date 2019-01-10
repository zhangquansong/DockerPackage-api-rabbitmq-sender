package com.clt.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : SenderConf
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009上午 11:15
 * @Description :
 **/
//@Configuration
public class SenderConfig {
//    @Bean
    public Queue queue() {
        return new Queue("queue");
    }
}

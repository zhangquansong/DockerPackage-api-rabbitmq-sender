package com.clt.api.config;

import com.clt.api.utils.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : RabbitConfig
 * @Author : zhangquansong
 * @Date : 2019/1/9 0009 上午 11:39
 * @Description :rabbit配置
 **/
@Slf4j
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbit.queue.name}")
    private String messageQueue;

    @Value("${spring.rabbit.topic.exchange}")
    private String topicExchange;

    @Value("${spring.rabbit.topic.routingkey}")
    private String routeKey;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue getMessageQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(messageQueue, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public TopicExchange exchangeFanout(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange(RabbitMqConstants.TOPIC_EXCHANGE);
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

    @Bean
    public Queue getMessageSMSQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqConstants.CLT_QUEUE_SMS, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue getMessageRewardQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqConstants.CLT_QUEUE_REWARD, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue getMessageAuthRewardQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqConstants.CLT_QUEUE_AUTHENTICATION, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public TopicExchange exchange(RabbitAdmin rabbitAdmin) {
        TopicExchange exchange = new TopicExchange(routeKey);
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Binding bindingExchange(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(getMessageQueue(rabbitAdmin)).to(exchange(rabbitAdmin)).with(routeKey);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * 配置广播路由器
     *
     * @return
     * @date 2018年5月4日 下午3:38:08
     * @author wangj@boruijinfu.com
     */
    @Bean
    public Binding bindingFanoutSMSExchange(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(getMessageSMSQueue(rabbitAdmin)).to(exchangeFanout(rabbitAdmin)).with(RabbitMqConstants.CLT_QUEUE_ROUTINGKEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * 配置广播路由器
     *
     * @return
     * @date 2018年5月4日 下午3:38:08
     * @author wangj@boruijinfu.com
     */
    @Bean
    public Binding bindingFanoutRewardExchange(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(getMessageRewardQueue(rabbitAdmin)).to(exchangeFanout(rabbitAdmin)).with(RabbitMqConstants.CLT_QUEUE_ROUTINGKEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * 配置广播路由器
     *
     * @return
     * @date 2018年5月4日 下午3:38:08
     * @author wangj@boruijinfu.com
     */
    @Bean
    public Binding bindingFanoutAuthRewardExchange(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(getMessageAuthRewardQueue(rabbitAdmin)).to(exchangeFanout(rabbitAdmin)).with(RabbitMqConstants.CLT_QUEUE_ROUTINGKEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public DirectExchange defaultExchange(RabbitAdmin rabbitAdmin) {
        DirectExchange directExchange = new DirectExchange(RabbitMqConstants.DEFAULT_EXCHANGE, true, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    public Queue repeatTradeQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME, true, false, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Binding repeatTradeBinding(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(repeatTradeQueue(rabbitAdmin)).to(defaultExchange(rabbitAdmin)).with(RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Queue deadLetterQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", RabbitMqConstants.DEFAULT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
        Queue queue = new Queue(RabbitMqConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, true, false, false, arguments);
        rabbitAdmin.declareQueue(queue);
        log.info("参数：{}", queue.getArguments());
        return queue;
    }

    @Bean
    public Binding deadLetterBinding(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(deadLetterQueue(rabbitAdmin)).to(defaultExchange(rabbitAdmin)).with(RabbitMqConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Queue queue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqConstants.NORMAL_QUEUE, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Binding binding(RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queue(rabbitAdmin)).to(defaultExchange(rabbitAdmin)).with(RabbitMqConstants.NORMAL_QUEUE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

}

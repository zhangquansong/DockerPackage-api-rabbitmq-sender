package com.clt.api.mq;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 发送短信对象
 * 
 * @date 2018年5月7日 上午10:36:22
 * @author zhaoyp@boruijinfu.com
 */
@Getter
@Setter
public class MessageMq {

    private Long usersId; // 用户编号
    
    private String mobile; //手机号

    private String type; // 短信类型
    
    private String desc; // 描述
    
    private String informTxt; //消息内容
    
    private String msgTxt; //短信内容

    public MessageMq(Long usersId, String mobile, String type, String desc, String informTxt, String msgTxt) {
        super();
        this.mobile = mobile;
        this.usersId = usersId;
        this.type = type;
        this.desc = desc;
        this.informTxt = informTxt;
        this.msgTxt = msgTxt;
    }

}

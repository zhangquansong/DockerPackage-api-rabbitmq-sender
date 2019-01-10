package com.clt.api.mq;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 活动mq对象
 * @date 2018年5月16日 下午10:13:35
 * @author chenxb@boruijinfu.com
 */
@Getter
@Setter
public class AwardMq {

    private Long usersId; // 用户编号

    private String type; // 奖励类型
    
    private String productType;

    private String mobile; //手机号
    
    private Long copies;//持有份数
    
    private Long couponShouyi;//预期理财券收益
    
    private Long expectEarnings;//预期收益总金额
    
    private Long money; // 投资金额
    
    private Date insertTime; // 投资时间

    public AwardMq() {
    }

    public AwardMq(Long usersId, String type, String productType, String mobile, Long copies, Long couponShouyi, Long expectEarnings, Long money, Date insertTime) {
        super();
        this.usersId = usersId;
        this.type = type;
        this.productType = productType;
        this.mobile = mobile;
        this.copies = copies;
        this.couponShouyi = couponShouyi;
        this.expectEarnings = expectEarnings;
        this.money = money;
        this.insertTime = insertTime;
    }
    
    
}

import com.clt.api.ApiRabbitmqSenderApplication;
import com.clt.api.mq.RewardMq;
import com.clt.api.sender.HelloSender;
import com.clt.api.sender.RabbitAckSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName : TestMQ
 * @Author : zhangquansong
 * @Date : 2019/1/8 0008下午 2:00
 * @Description :
 **/
@SpringBootTest(classes = ApiRabbitmqSenderApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMQ {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private RabbitAckSender rabbitAckSender;

    @Test
    public void testRabbit() {
//        helloSender.send("zqstestqueue");
        for (int i = 0; i < 10; i++) {
//            helloSender.send("zqstestqueue");
            RewardMq rewardMq = new RewardMq(Long.valueOf(i), "15007257865", "bind", "认证");
            rabbitAckSender.sendReward(rewardMq);
            System.out.println("<><><><><>");
        }
    }
}

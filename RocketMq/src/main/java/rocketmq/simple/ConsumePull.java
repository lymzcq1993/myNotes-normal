package rocketmq.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.RocketmqConst;

import java.util.List;

/**
 * @author hujian
 * @date 2022/5/28 12:13
 */
public class ConsumePull {
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(RocketmqConst.SIMPLE_C_PULL_GROUP);
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(RocketmqConst.SIMPLE_TOPIC,"*");
        consumer.start();
            while (true){
                List<MessageExt> messageExts = consumer.poll();
                for (MessageExt messageExt : messageExts) {
                    System.out.println("收到了消息producer的消息---");
                    System.out.println("消息内容:"+new String(messageExt.getBody()));
                }
            }
        }
}

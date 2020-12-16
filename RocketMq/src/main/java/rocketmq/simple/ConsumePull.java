package rocketmq.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.RocketmqConst;

import java.util.List;

/**
 * @author hujian
 * @description
 * @date 2020/12/16 11:05
 */
public class ConsumePull {
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(RocketmqConst.SIMPLE_C_PULL_GROUP);
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(RocketmqConst.SIMPLE_TOPIC,"*");
        consumer.start();
        try {
            while (true){
                List<MessageExt> messageExts = consumer.poll();
                //System.out.println(messageExts);
                System.out.println(new String(messageExts.get(0).getBody()));
            }
        }catch (Exception e){

        }
    }
}

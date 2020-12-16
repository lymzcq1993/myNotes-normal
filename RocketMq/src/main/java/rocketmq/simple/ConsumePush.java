package rocketmq.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.RocketmqConst;

import java.util.List;

/**
 * @author hujian
 * @description
 * @date 2020/12/16 10:46
 */
public class ConsumePush {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketmqConst.SIMPLE_C_PUSH_GROUP);
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        consumer.subscribe(RocketmqConst.SIMPLE_TOPIC,"*");
        consumer.setConsumeTimestamp("20181109221800");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("simple push consume收到了消息"+list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("simple consume start");
    }
}

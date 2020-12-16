package rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.RocketmqConst;

import java.util.List;

/**
 * @author hujian
 * @description
 * @date 2020/12/16 11:57
 */
public class ConsumeOrder {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketmqConst.ORDER_C_GROUP);
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        consumer.subscribe(RocketmqConst.ORDER_TOPIC,"*");
        consumer.setConsumeTimestamp("20181109221800");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                System.out.println("order push consume收到了消息"+new String(list.get(0).getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("order consume start");
    }
}

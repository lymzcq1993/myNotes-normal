package rocketmq.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import rocketmq.RocketmqConst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义拉取消息从哪里开始消费
 * @author hujian
 * @date 2022/5/28 12:13
 */
public class ConsumeAssignPull {
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(RocketmqConst.SIMPLE_C_PULL_GROUP);
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        //设置手动提交
        consumer.setAutoCommit(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.start();
//        consumer.subscribe(RocketmqConst.SIMPLE_TOPIC,"*");
        Collection<MessageQueue> messageQueues = consumer.fetchMessageQueues(RocketmqConst.SIMPLE_TOPIC);
        ArrayList<MessageQueue> queuesList = new ArrayList<>(messageQueues);
        ArrayList<MessageQueue> assignList = new ArrayList<>();
        //例如一共有8个队列，获取生产者中的前4个队列
        for(int i =0;i<queuesList.size()/2;i++){
            assignList.add(queuesList.get(i));
        }
        consumer.assign(assignList);
        consumer.seek(assignList.get(0),10);
        while (true){
            List<MessageExt> messageExts = consumer.poll();
            System.out.printf("%s %n",messageExts);
            consumer.commitSync();
        }
    }
}

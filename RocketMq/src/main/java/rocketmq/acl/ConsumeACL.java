package rocketmq.acl;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import rocketmq.RocketmqConst;

import java.util.List;

/**ACL消费者
 * @author hujian
 * @since 2022-05-28 12:37
 */
public class ConsumeACL {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("groupB",getAclRPCHook(),new AllocateMessageQueueAveragely());
        consumer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        //具体可以参考官方sql语法
        consumer.subscribe("topicA","*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt message : messages) {
                    // 打印接收消息的时间
                    System.out.println("Receive message[msgId=" + message.getMsgId() + "] " + new String(message.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("ACL consume start");
    }


    static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials("hujianMQ","123456789"));
    }
}

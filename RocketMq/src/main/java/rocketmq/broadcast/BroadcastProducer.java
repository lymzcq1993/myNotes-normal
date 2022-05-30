package rocketmq.broadcast;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

/**基本类型，单向发送消息
 * @author hujian
 * @date 2022/5/28 10:31
 */
public class BroadcastProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.BROADCAST_P_GROUP);
        //设置队列数量，默认4
        producer.setDefaultTopicQueueNums(8);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(RocketmqConst.BROADCAST_TOPIC,"broadcast_tag",("broadcast:"+i).getBytes());
            try {
                producer.sendOneway(message);
            } catch (RemotingException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(50000);
        producer.shutdown();
    }
}

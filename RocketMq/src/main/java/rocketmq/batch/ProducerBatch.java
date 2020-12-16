package rocketmq.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

import java.util.ArrayList;
import java.util.List;

/**批量消息
 * @author hujian
 * @description
 * @date 2020/12/16 12:13
 */
public class ProducerBatch {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.BATCH_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        List<Message> messageList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Message message = new Message(RocketmqConst.BATCH_TOPIC,RocketmqConst.BATCH_TAG,("batchMessgae"+i).getBytes());
            messageList.add(message);
        }
        SendResult send = producer.send(messageList);
        producer.shutdown();
    }

}

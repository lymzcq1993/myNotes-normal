package rocketmq.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

/**延迟消息
 * @author hujian
 * @description
 * @date 2020/12/16 12:13
 */
public class ProducerDelay {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.DELAY_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(RocketmqConst.DELAY_TOPIC,RocketmqConst.DELAY_TAG,("delaymessgae"+i).getBytes());
            //1到18分别对应messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(3);
            SendResult send = producer.send(message);
        }
        producer.shutdown();
    }

}

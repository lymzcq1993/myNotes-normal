package rocketmq.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

import java.text.SimpleDateFormat;

/**延迟消息
 * @author hujian
 * @date 2022/5/28 10:31
 */
public class ProducerDelay {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.DELAY_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 5; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Message message = new Message(RocketmqConst.DELAY_TOPIC,RocketmqConst.DELAY_TAG,("delaymessgae"+i).getBytes());
            //1到18分别对应messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //再大的需要购买rocket商业版本进行支持
            message.setDelayTimeLevel(i);
            producer.send(message);
        }
        producer.shutdown();
    }

}

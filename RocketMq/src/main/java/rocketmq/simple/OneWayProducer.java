package rocketmq.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

/**基本类型，单向发送消息
 * @author hujian
 * @description
 * @date 2020/12/16 10:31
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.SIMPLE_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 1; i++) {
            Message message = new Message(RocketmqConst.SIMPLE_TOPIC,RocketmqConst.SIMPLE_TAG,("sendOnewayMessage:"+i).getBytes());
            try {
                producer.sendOneway(message);
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(50000);
        producer.shutdown();
    }
}

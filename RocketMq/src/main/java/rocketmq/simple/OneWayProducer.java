package rocketmq.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

/**基本类型，单向发送消息
 * @author hujian
 * @date 2022/5/28 10:31
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.SIMPLE_P_GROUP);
        //设置队列数量，默认4
        producer.setDefaultTopicQueueNums(8);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 100; i++) {
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

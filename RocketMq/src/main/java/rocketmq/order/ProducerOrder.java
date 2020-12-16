package rocketmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

import java.util.List;

/**顺序消息
 * @author hujian
 * @description
 * @date 2020/12/16 11:32
 */
public class ProducerOrder {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.ORDER_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 10; i++) {
            int order = i;
            for (int j = 0; j < 5; j++) {
                Message message = new Message(RocketmqConst.ORDER_TOPIC,"order_"+order,"KEY_"+order,
                        ("order_"+order+",step"+j).getBytes());
                try {
                    SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                        /**
                         * 第三个参数是send()方法第三个参数传进来的业务参数
                         * @param list
                         * @param message
                         * @param o
                         * @return
                         */
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            Integer orderId = (Integer) o;
                            //这里要保证一个order的消息都放进同一个队列
                            System.out.println(list.size());
                            int index = orderId % list.size();
                            return list.get(index);
                        }
                    }, order);
                    System.out.println(sendResult);
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                }

            }

        }
        producer.shutdown();
    }

}

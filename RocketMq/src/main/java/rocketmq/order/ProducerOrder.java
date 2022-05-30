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
 * @date 2022/5/28 10:31
 */
public class ProducerOrder {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(RocketmqConst.ORDER_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);

        producer.start();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                Message message = new Message(RocketmqConst.ORDER_TOPIC,"order_"+i,"KEY_"+i,
                        ("order_"+i+",step"+j).getBytes());
                try {
                    //select选择器用来控制发送到哪个队列，有3个实现
                    //SelectMessageQueueByHash 通过hash来保证存取到同一个队列,这里的重写等同于该实现类
                    //SelectMessageQueueByMachineRoom    通过机器指定
                    //SelectMessageQueueByRandom   随机分发
                    SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                        /**
                         * 第三个参数是send()方法第三个参数传进来的业务参数
                         * @param list
                         * @param message
                         * @param o
                         * @return
                         */
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            Integer orderId = (Integer) o;
                            //这里要保证一个order的消息都放进同一个队列
                            System.out.println(list.size());
                            int index = orderId % list.size();
                            return list.get(index);
                        }
                    }, i);
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

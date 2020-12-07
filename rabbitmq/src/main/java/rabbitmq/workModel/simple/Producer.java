package rabbitmq.workModel.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/** 简单模式，该模式下无需指定exchange和routing key，生产者发往指定队列，消费者直接从队列中取得消息
 * 没有使用exchange
 * @author hujian
 * @Classname Producer
 * @Description
 * @Date 2020/11/29 15:12
 */
public class Producer {
    public static void main(String[] args)  {
        try (Connection conncet = RabbitMqConnect.getConnect();
             Channel channel = conncet.createChannel()){
            //1.指定队列  2.是否持久化  3.是否私有化，false代表只有第一次访问它的消费者能一直使用，拒绝其余消费者使用  4.是否自动删除，false表示连接断开后不会删除队列 5.额外参数
            channel.queueDeclare(RabbitMqConstant.SIMPLE_QUEUE_HUJIAN,false,false,false,null);


            String message = "hello rabbitmq";
            //1.exchange 2.队列 3.额外属性  4.消息体
            channel.basicPublish("",RabbitMqConstant.SIMPLE_QUEUE_HUJIAN,null,message.getBytes());
            System.out.println("发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
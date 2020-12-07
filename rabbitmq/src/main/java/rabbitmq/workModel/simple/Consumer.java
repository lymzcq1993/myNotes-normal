package rabbitmq.workModel.simple;

import com.rabbitmq.client.*;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/**
 * @author hujian
 * @Classname Consumer
 * @Date 2020/11/29 15:41
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
       Connection connect = RabbitMqConnect.getConnect();
       Channel channel = connect.createChannel();

       //1.指定队列  2.是否持久化  3.是否私有化，false代表只有第一次访问它的消费者能一直使用，拒绝其余消费者使用  4.是否自动删除，false表示连接断开后不会删除队列 5.额外参数
       channel.queueDeclare(RabbitMqConstant.SIMPLE_QUEUE_HUJIAN, false, false, false, null);

       channel.basicConsume(RabbitMqConstant.SIMPLE_QUEUE_HUJIAN,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String str=new String(body);
               System.out.println("简单队列:消费了消息"+str);
               System.out.println("tagId:"+envelope.getDeliveryTag());
               //false代表只签收当前的消息，true签收该消费者所有没签收的消息
               channel.basicAck(envelope.getDeliveryTag(),false);
           }

           @Override
           public void handleConsumeOk(String consumerTag) {
               //super.handleConsumeOk(consumerTag);
               System.out.println(consumerTag);
           }
       });


    }
}
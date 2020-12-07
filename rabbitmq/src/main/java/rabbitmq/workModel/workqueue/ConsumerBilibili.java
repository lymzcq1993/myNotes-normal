package rabbitmq.workModel.workqueue;

import com.rabbitmq.client.*;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/**
 * @author hujian
 * @Classname Consumer
 * @Description
 * @Date 2020/11/29 15:41
 */
public class ConsumerBilibili {
    public static void main(String[] args) throws IOException {
       Connection connect = RabbitMqConnect.getConnect();
       Channel channel = connect.createChannel();

       channel.queueDeclare(RabbitMqConstant.WORK_Q_HUJIAN, false, false, false, null);
        channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.WORK_Q_HUJIAN,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("B站收到了:"+json);
               //System.out.println("tagId:"+envelope.getDeliveryTag());
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               channel.basicAck(envelope.getDeliveryTag(),false);
           }

       });


    }
}
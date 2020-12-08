package rabbitmq.confirm;

import com.rabbitmq.client.*;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/**
 * @author hujian
 * @Classname Wuhan
 * @Description
 * @Date 2020/11/29 15:41
 */
public class Wuhan {
    public static void main(String[] args) throws IOException {
       Connection conncet = RabbitMqConnect.getConnect();
       Channel channel = conncet.createChannel();

       channel.queueDeclare(RabbitMqConstant.CONFIRM_Q_HUJIAN, false, false, false, null);
        channel.queueBind(RabbitMqConstant.CONFIRM_Q_HUJIAN,RabbitMqConstant.CONFIRM_EX_HUJIAN,"china.#");
        channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.CONFIRM_Q_HUJIAN,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("武汉收到了:"+json);
               //System.out.println("tagId:"+envelope.getDeliveryTag());
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               channel.basicAck(envelope.getDeliveryTag(),false);
           }

       });


    }
}
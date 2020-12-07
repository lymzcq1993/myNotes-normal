package rabbitmq.workModel.pubsub;

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
public class Wuhan {
    public static void main(String[] args) throws IOException {
       Connection conncet = RabbitMqConnect.getConnect();
       Channel channel = conncet.createChannel();

       channel.queueDeclare(RabbitMqConstant.FANOUT_Q_WUHAN, false, false, false, null);
       //队列绑定交换机 1.队列名称  2：exchange   3：routing key  4：额外参数
       channel.queueBind(RabbitMqConstant.FANOUT_Q_WUHAN,RabbitMqConstant.FANOUT_EX_WEATHER,"");
       channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.FANOUT_Q_WUHAN,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("fanout武汉收到了:"+json);
               channel.basicAck(envelope.getDeliveryTag(),false);
           }

       });


    }
}
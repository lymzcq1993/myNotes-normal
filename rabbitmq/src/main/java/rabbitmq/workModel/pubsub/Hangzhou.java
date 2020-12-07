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
public class Hangzhou {
    public static void main(String[] args) throws IOException {
       Connection connect = RabbitMqConnect.getConnect();
       Channel channel = connect.createChannel();

       channel.queueDeclare(RabbitMqConstant.FANOUT_Q_HANGZHOU, false, false, false, null);
        //1：队列  2：交换机  3：路由key
        channel.queueBind(RabbitMqConstant.FANOUT_Q_HANGZHOU,RabbitMqConstant.FANOUT_EX_WEATHER,"");
        channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.FANOUT_Q_HANGZHOU,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("fanout杭州收到了:"+json);
               channel.basicAck(envelope.getDeliveryTag(),false);
           }

       });


    }
}
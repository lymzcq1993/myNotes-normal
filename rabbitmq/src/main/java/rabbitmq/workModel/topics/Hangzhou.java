package rabbitmq.workModel.topics;

import com.rabbitmq.client.*;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/** topic通配符模式
 * @author hujian
 * @Classname Consumer
 * @Description
 * @Date 2020/11/29 15:41
 */
public class Hangzhou {
    public static void main(String[] args) throws IOException {
       Connection conncet = RabbitMqConnect.getConnect();
       Channel channel = conncet.createChannel();

       channel.queueDeclare(RabbitMqConstant.TOPIC_Q_HANGZHOU, false, false, false, null);
        //1：队列  2：交换机  3：路由key
        channel.queueBind(RabbitMqConstant.TOPIC_Q_HANGZHOU,RabbitMqConstant.TOPIC_EX_WEATHER,"china.hangzhou.*");
        channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.TOPIC_Q_HANGZHOU,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("topics:杭州收到了:"+json);
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
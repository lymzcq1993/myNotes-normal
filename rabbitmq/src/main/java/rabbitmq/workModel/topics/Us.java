package rabbitmq.workModel.topics;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/** topic通配符模式
 * @author hujian
 * @Classname Us
 * @Description
 * @Date 2020/11/29 15:41
 */
public class Us {
    private Logger log = LoggerFactory.getLogger(Us.class);
    public static void main(String[] args) throws IOException {
       Connection connect = RabbitMqConnect.getConnect();
       Channel channel = connect.createChannel();

       channel.queueDeclare(RabbitMqConstant.TOPIC_Q_US, false, false, false, null);
       channel.queueBind(RabbitMqConstant.TOPIC_Q_US,RabbitMqConstant.TOPIC_EX_WEATHER,"us.*");
       //每签收一个再收取下一条消息
       channel.basicQos(1);
       channel.basicConsume(RabbitMqConstant.TOPIC_Q_US,false,new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String json=new String(body);
               System.out.println("美国收到了:"+json);
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
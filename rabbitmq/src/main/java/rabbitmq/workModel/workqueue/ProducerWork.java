package rabbitmq.workModel.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**工作队列模式，多个消费者同时消费消息。
 * 没有使用exchange
 * @author hujian
 * @Classname Producer
 * @Description
 * @Date 2020/11/29 15:12
 */
public class ProducerWork {
    public static void main(String[] args)  {
        try (Connection connect = RabbitMqConnect.getConnect();
             Channel channel = connect.createChannel()){

            channel.queueDeclare(RabbitMqConstant.WORK_Q_HUJIAN,false,false,false,null);

            for (int i =0;i<100;i++){
                String message = "发布了今天的第" + i + "次天气预报";
                channel.basicPublish("",RabbitMqConstant.WORK_Q_HUJIAN,null,message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
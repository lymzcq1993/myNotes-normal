package rabbitmq.workModel.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/** 发布订阅模式，没有指定routing key
 * 使用exchange
 * @author hujian
 * @Classname Producer
 * @Description
 * @Date 2020/11/29 15:12
 */
public class PubWeather {
    public static void main(String[] args)  {
        try (Connection connect = RabbitMqConnect.getConnect();
             Channel channel = connect.createChannel()){


            for (int i =0;i<5;i++){
                String message = "今天的第" + i + "次天气预报";
                channel.basicPublish(RabbitMqConstant.FANOUT_EX_WEATHER,"",null,message.getBytes());
                System.out.println("fanout:发布："+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
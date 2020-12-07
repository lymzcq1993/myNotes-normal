package rabbitmq.workModel.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/** topic通配符模式  这里topics和routing模式一起，以topics为例，
 * routing是固定的值，exchange对应direct，
 * topics可以匹配符号 exchange对应topics  *匹配.前的一个值    #匹配所有值
 * @author hujian
 * @Classname Producer
 * @Description
 * @Date 2020/11/29 15:12
 */
public class TopicWeather {
    public static void main(String[] args)  {
        try (Connection conncet = RabbitMqConnect.getConnect();
             Channel channel = conncet.createChannel()){

            Map<String,String> map = new HashMap<String,String>();
            map.put("china.wuhan.1130","武汉的1130的天气");
            map.put("china.wuhan.1129","武汉的1129的天气");
            map.put("china.hangzhou.1130","杭州的1130的天气");
            map.put("us.1130","美国的1130的天气");
            map.put("english","英国的1129的天气");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                channel.basicPublish(RabbitMqConstant.TOPIC_EX_WEATHER,entry.getKey(),null,entry.getValue().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
package rabbitmq.confirm;

import com.rabbitmq.client.*;
import rabbitmq.utils.RabbitMqConnect;
import rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/** 监听confirm，确认消息是否被broker接收
 * @author hujian
 * @Classname Producer
 * @Description
 * @Date 2020/11/29 15:12
 */
public class ConfirmWeather {
    public static void main(String[] args)  {
        try {
            //监听confirm所以不能关闭连接
            Connection connect = RabbitMqConnect.getConnect();
            Channel channel = connect.createChannel();
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long l, boolean b) throws IOException {
                    System.out.println("消息已被签收"+l+"mm"+b);
                }
                @Override
                public void handleNack(long l, boolean b) throws IOException {
                    System.out.println("消息已被拒收");
                }
            });
            channel.addReturnListener(aReturn -> {
                System.out.println("============ReturnCallback===================");
                System.out.println("-----"+aReturn.getReplyText());
            });

            channel.addReturnListener((i, s, s1, s2, basicProperties, bytes) -> {
                System.out.println("============ReturnListener===================");
                System.out.println(i+":s:"+s+"s1:"+s1+"s2:"+s2);
            });

            for (int i =0;i<5;i++){
                String message = "今天的第" + i + "次天气预报"+i;
                channel.basicPublish(RabbitMqConstant.CONFIRM_EX_HUJIAN,"china.hubei"+i,true,null,message.getBytes());
                Thread.sleep(100);
                System.out.println("confirm:发布："+message);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
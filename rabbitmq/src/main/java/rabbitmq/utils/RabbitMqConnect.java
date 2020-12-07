package rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hujian
 * @Classname RabbitMqConnect
 * @Description
 * @Date 2020/11/29 15:12
 */
public class RabbitMqConnect {
    private static ConnectionFactory connect = new ConnectionFactory();
    static {
        connect.setHost("192.168.31.101");
        connect.setPort(5672);
        connect.setUsername("hujian");
        connect.setPassword("hujian");
        connect.setVirtualHost("/hujian");
    }

    public static Connection getConnect(){
        Connection con;
        try {
             con = connect.newConnection();
            return con;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return  null;
        }
    }

}
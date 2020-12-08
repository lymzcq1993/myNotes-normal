package rabbitmq.utils;

/**
 * @author hujian
 * @Classname RabbitMqConstant
 * @Description
 * @Date 2020/11/29 15:43
 */
public class RabbitMqConstant {
    /********** 简单模式   ***************/
    public static final String SIMPLE_QUEUE_HUJIAN = "simple.hujian";
    public static final String SIMPLE_Q_WEATHER = "simple.weather";

    /**********  工作模式  *************/
    public static final String WORK_Q_HUJIAN = "work.hujian";

    /********* 广播模式交换机和队列   ***************/
    public static final String FANOUT_Q_WUHAN = "fanout.wuhan";
    public static final String FANOUT_Q_HANGZHOU = "fanout.hangzhou";
    public static final String FANOUT_EX_WEATHER = "fanout.exchange";

    /********  通配符模式交换机和队列  *************/
    public static final String TOPIC_Q_WUHAN = "topic.wuhan";
    public static final String TOPIC_Q_HANGZHOU = "topic.hangzhou";
    public static final String TOPIC_Q_US = "topic.us";
    public static final String TOPIC_EX_WEATHER = "topic.exchange";

    /******** confirm   **************/
    public static final String CONFIRM_EX_HUJIAN = "confirm.exchange";
    public static final String CONFIRM_Q_HUJIAN = "confirm.hujian";
}
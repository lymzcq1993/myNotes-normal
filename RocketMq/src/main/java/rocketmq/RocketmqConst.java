package rocketmq;

/**
 * @author hujian
 * @description
 * @date 2020/12/16 10:33
 */
public class RocketmqConst {
    public static final String NAMESRV_ADDRS = "192.168.5.103:9876;192.168.5.100:9876";

    public static final String SIMPLE_P_GROUP = "simple_p_group";
    public static final String SIMPLE_C_PULL_GROUP = "simple_c_pull_group";
    public static final String SIMPLE_C_PUSH_GROUP = "simple_c_push_group";
    public static final String SIMPLE_TOPIC = "simple_topic";
    public static final String SIMPLE_TAG = "simple_tag";

    public static final String ORDER_P_GROUP = "order_p_group";
    public static final String ORDER_C_GROUP = "order_c_group";
    public static final String ORDER_TOPIC = "order_topic";
    public static final String ORDER_TAG = "order_tag";

    public static final String DELAY_P_GROUP = "delay_p_group";
    public static final String DELAY_C_GROUP = "delay_c_group";
    public static final String DELAY_TOPIC = "delay_topic";
    public static final String DELAY_TAG = "delay_tag";

    public static final String BATCH_P_GROUP = "batch_p_group";
    public static final String BATCH_C_GROUP = "batch_c_group";
    public static final String BATCH_TOPIC = "batch_topic";
    public static final String BATCH_TAG = "batch_tag";

    /*************事务 ***************/
    public static final String TRANS_P_GROUP = "trans_p_group";
    public static final String TRANS_C_GROUP = "trans_c_group";
    public static final String TRANS_TOPIC = "trans_topic";
    public static final String TRANS_TAG = "trans_tag";
}

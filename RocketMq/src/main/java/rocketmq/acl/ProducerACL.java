package rocketmq.acl;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

import java.util.ArrayList;
import java.util.List;

/**权限测试
 * @author hujian
 * @since 2022-05-28 12:37
 */
public class ProducerACL {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("groupA",getAclRPCHook());
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        producer.start();
        List<Message> messageList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Message message = new Message("topicB",RocketmqConst.FILTER_TAG+i,("filter"+i).getBytes());
//            messageList.add(message);
            producer.send(message);
        }
//        producer.send(messageList);
//        producer.shutdown();
    }


    static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials("hujianMQ","123456789"));
    }
}

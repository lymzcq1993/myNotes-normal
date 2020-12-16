package rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**事务消息
 * @author hujian
 * @description
 * @date 2020/12/16 12:13
 */
public class ProducerTransaction {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer(RocketmqConst.TRANS_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
        ExecutorService executorService =new ThreadPoolExecutor(2,5,100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),new BasicThreadFactory.Builder().namingPattern("rocketmq-transaction-thread").build());
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();

        String[] tags = {"TagA","TagB","TagC","TagD"};
        for (int i = 0; i < 10; i++) {
            Message message = new Message(RocketmqConst.TRANS_TOPIC,tags[i%tags.length],"KEYS_"+i,("transMessage"+i).getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
            Thread.sleep(10);
        }
    }

    private static class TransactionListenerImpl implements TransactionListener{
        private AtomicInteger transactionIndex = new AtomicInteger(0);

        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        /**
         *    COMMIT_MESSAGE:消费者立即收到消息
         *    ROLLBACK_MESSAGE:被丢弃
         *    UNKNOW:会过一段时间再来执行checkLocalTransaction方法
         * @param message
         * @param o
         * @return
         */
        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {
            String tags = message.getTags();
            if(StringUtils.contains(tags,"TagA")){
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            else if (StringUtils.contains(tags,"TagB")){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            else{
                return LocalTransactionState.UNKNOW;
            }
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            String tags = messageExt.getTags();
            if(StringUtils.contains(tags,"TagC")){
                return LocalTransactionState.COMMIT_MESSAGE;
            }else if(StringUtils.contains(tags,"TagD")){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }else{
                return LocalTransactionState.UNKNOW;
            }
        }
    }

}

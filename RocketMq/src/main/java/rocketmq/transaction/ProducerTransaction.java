package rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.RocketmqConst;

/**事务消息
 * @author hujian
 * @date 2022/5/30 12:13
 */
public class ProducerTransaction {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //创建一个事务监听器
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer(RocketmqConst.TRANS_P_GROUP);
        producer.setNamesrvAddr(RocketmqConst.NAMESRV_ADDRS);
//        ExecutorService executorService =new ThreadPoolExecutor(2,5
//                ,100, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(2000)
//                ,new BasicThreadFactory.Builder().namingPattern("rocketmq-transaction-thread").build());
//        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();

        //tagA发送3次，tagB发送3次，tagC发送2次，tagD发送2次
        String[] tags = {"tagA","tagB","tagC","tagD"};
        for (int i = 0; i < 10; i++) {
            String tag = tags[i % tags.length];
            Message message = new Message(RocketmqConst.TRANS_TOPIC, tag,"KEY"+i,("transMessage-----"+tag).getBytes());
            //使用事务消息发送
            producer.sendMessageInTransaction(message,null);
            System.out.println("tag:"+tag+",message:"+i);
            Thread.sleep(10);
        }
//        producer.shutdown();
    }

    /**
     *    事务消息监听实现类
     *    COMMIT_MESSAGE:消费者立即收到消息
     *    ROLLBACK_MESSAGE:被丢弃
     *    UNKNOW:会过一段时间再来执行checkLocalTransaction方法，默认次数是15
     */
    public static class TransactionListenerImpl implements TransactionListener{
        /**
         * 此处检查broker是否接收到消息
         * 接收到消息之后执行本地事务逻辑
         * @param message
         * @param o
         * @return
         */
        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {
            //此处是tagA提交，tagB丢弃
            String tags = message.getTags();
            if(StringUtils.contains(tags,"tagA")){
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            else if (StringUtils.contains(tags,"tagB")){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            else{
                return LocalTransactionState.UNKNOW;
            }
        }

        /**
         * 检查本地事务是否玩完成，响应给broker响应的状态
         * 此处因实现的是自己的逻辑
         * @param messageExt
         * @return
         */
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            //此处提交tagc，丢弃tagD
            String tags = messageExt.getTags();
            if(StringUtils.contains(tags,"tagC")){
                return LocalTransactionState.COMMIT_MESSAGE;
            }else if(StringUtils.contains(tags,"tagD")){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }else{
                return LocalTransactionState.UNKNOW;
            }
        }
    }

}

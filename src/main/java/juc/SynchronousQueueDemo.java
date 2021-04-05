package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 53137
 */
@Slf4j(topic = "c.SynchronousQueueDemo")
public class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new SynchronousQueue<>();


        new Thread(() -> {
            try {
                log.info("put:{}", "1");
                queue.put("1");
                log.info("put:{}", "2");
                queue.put("2");
                log.info("put:{}", "3");
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "put").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("take:{}", queue.take());
                TimeUnit.SECONDS.sleep(2);
                log.info("take:{}", queue.take());
                TimeUnit.SECONDS.sleep(2);
                log.info("take:{}", queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "take").start();
    }
}

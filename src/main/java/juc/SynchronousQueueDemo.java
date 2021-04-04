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
            for (int i = 1; i <= 3; i++) {
                String value = String.valueOf(i);
                try {
                    log.info("put:{}", value);
                    queue.put(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "put").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    log.info("take:{}", queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "take").start();
    }
}

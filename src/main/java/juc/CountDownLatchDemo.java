package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时锁
 *
 * @author 53137
 */
@Slf4j(topic = "c.CountDownLatchDemo")
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                log.debug("{}离开教室", Thread.currentThread().getName());
                latch.countDown();
            }, String.valueOf(i)).start();
        }
        latch.await();
        log.debug("{}关门", Thread.currentThread().getName());
    }
}

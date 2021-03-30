package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 *
 * @author 53137
 */
@Slf4j(topic = "c.SemaphoreDemo")
public class SemaphoreDemo {
    public static void main(String[] args) {
        /**
         * 初始化一个信号量为3，默认是false 非公平锁， 模拟3个停车位
         */
        Semaphore semaphore = new Semaphore(3, false);
        for (int i = 1; i <= 6; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("第{}辆车抢到车位", index);
                try {
                    TimeUnit.SECONDS.sleep(2);
                    log.debug("停车中...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
                log.debug("第{}辆车开走", index);
            }).start();
        }
    }
}

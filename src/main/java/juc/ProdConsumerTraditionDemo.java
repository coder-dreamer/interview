package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者线程，1和0交替输出
 *
 * @author 53137
 */
public class ProdConsumerTraditionDemo {
    public static void main(String[] args) throws InterruptedException {
        ShareData share = new ShareData();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 5; i++) {
            executorService.execute(share::increase);
        }
        for (int i = 1; i <= 5; i++) {
            executorService.execute(share::reduce);
        }
        executorService.shutdown();
    }
}

@Slf4j(topic = "c.ShareData")
class ShareData {
    private volatile AtomicInteger number = new AtomicInteger(0);
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 增加
     */
    public void increase() {
        lock.lock();
        try {
            while (number.get() != 0) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(1);
            log.info("number:{}", number.incrementAndGet());
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 减少
     */
    public void reduce() {
        lock.lock();
        try {
            while (number.get() == 0) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(1);
            log.info("number:{}", number.decrementAndGet());
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 两个线程t1和t2，t1先获取锁并获取成功，t2后获取锁，无法成功获取，只能自旋等待。直到t1释放锁以后，t2成功获取到锁
 * 自旋锁演示
 *
 * @author 53137
 */
@Slf4j(topic = "c.SpinLockDemo")
public class SpinLockDemo {
    private AtomicReference<Thread> lock = new AtomicReference();

    public void myLock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        log.debug("开始获取锁");
        while (!lock.compareAndSet(null, thread)) {
            TimeUnit.MILLISECONDS.sleep(500);
            log.debug("未获取到锁");
        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        log.debug("释放锁");
        if (lock.compareAndSet(thread, null)) {
            log.debug("释放锁成功");
        }
    }

    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();
        new Thread(() -> {
            try {
                demo.myLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                demo.myUnlock();
            }
        }, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                demo.myLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.myUnlock();
        }, "t2").start();
    }
}

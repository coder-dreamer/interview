package juc;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序打印AA5次，BB10次，CC15次
 *
 * @author 53137
 */
@Slf4j(topic = "c.SequentialPrintingDemo")
public class SequentialPrintingDemo {
    public static void main(String[] args) {
        PrintDemo demo = new PrintDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    demo.print5();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "A").start();
            new Thread(() -> {
                try {
                    demo.print10();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "B").start();
            new Thread(() -> {
                try {
                    demo.print15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "C").start();
        }
    }
}

@Slf4j(topic = "c.printDemo")
class PrintDemo {
    /**
     * 1->A；2->B；3->C
     */
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();

    public void print5() throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                a.await();
            }
            log.info("===========");
            for (int i = 0; i < 5; i++) {
                log.info("AA");
            }
            //唤醒B
            number = 2;
            b.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    public void print10() throws InterruptedException {
        lock.lock();
        try {
            while (number != 2) {
                b.await();
            }
            for (int i = 0; i < 10; i++) {
                log.info("BB");
            }
            //唤醒C
            number = 3;
            c.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15() throws InterruptedException {
        lock.lock();
        try {
            while (number != 3) {
                c.await();
            }
            for (int i = 0; i < 15; i++) {
                log.info("CC");
            }
            //唤醒A
            number = 1;
            a.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

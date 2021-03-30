package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障
 *
 * @author 53137
 */
@Slf4j(topic = "c.CyclicBarrierDemo")
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /**
         * 定义一个循环屏障，参数1：需要累加的值，参数2 需要执行的方法
         */
        CyclicBarrier cyclic = new CyclicBarrier(7, () -> {
            log.debug("召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            int index = i;
            new Thread(() -> {
                log.debug("集齐第{}颗龙珠", index);
                try {
                    // 先到的被阻塞，等全部线程完成后，才能执行方法
                    cyclic.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

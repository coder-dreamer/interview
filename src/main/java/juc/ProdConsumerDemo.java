package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者线程3.0版
 *
 * @author 53137
 */
@Slf4j(topic = "c.ProdConsumerDemo")
public class ProdConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        MyResource resource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            try {
                log.info("开始生产");
                resource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                log.info("开始消费");
                resource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        TimeUnit.SECONDS.sleep(5);
        log.info("停止");
        resource.stop();
    }
}

@Slf4j(topic = "c.MyResource")
class MyResource {
    /**
     * 开始生产和结束生产标记，true->开始；false->结束
     */
    private volatile boolean FLAG = true;
    /**
     *
     */
    private AtomicInteger number = new AtomicInteger();

    BlockingQueue<String> queue = null;

    public MyResource(BlockingQueue<String> queue) {
        this.queue = queue;
        System.out.println(queue.getClass().getName());
    }

    /**
     * 生产
     */
    public void prod() throws InterruptedException {
        String data = null;
        boolean offer;
        while (FLAG) {
            data = number.incrementAndGet() + "";
            offer = queue.offer(data, 2L, TimeUnit.SECONDS);
            if (offer) {
                log.info("插入队列成功，值是{}", data);
            } else {
                log.info("插入队列失败，值是{}", data);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        log.info("停止生产");
    }

    /**
     * 消费
     */
    public void consumer() throws InterruptedException {
        while (true) {
            String poll = queue.poll(2L, TimeUnit.SECONDS);
            if (poll != null && poll != "") {
                log.info("从队列中取出元素成功，值是{}", poll);
            } else {
                log.info("队列中没有内容");
                FLAG = false;
                return;
            }
        }
    }

    /**
     * 停止生产
     */
    public void stop() {
        this.FLAG = false;
    }
}

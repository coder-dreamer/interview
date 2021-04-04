package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *
 * @author 53137
 */
@Slf4j(topic = "c.BlockingQueueDemo")
public class BlockingQueueDemo {
    public static void main(String[] args) {
        //test();
        //test1();
        //test2();
        test3();

    }

    private static void test3() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        try {
            //true
            log.info("offer:{}", queue.offer("1", 2L, TimeUnit.SECONDS));
            //true
            log.info("offer:{}", queue.offer("2", 2L, TimeUnit.SECONDS));
            //true
            log.info("offer:{}", queue.offer("3", 2L, TimeUnit.SECONDS));
            //阻塞2秒，2秒之后如果添加不进去直接放弃
            log.info("offer:{}", queue.offer("4", 2L, TimeUnit.SECONDS));
            //1
            log.info("poll:{}", queue.poll(2L, TimeUnit.SECONDS));
            //2
            log.info("poll:{}", queue.poll(2L, TimeUnit.SECONDS));
            //3
            log.info("poll:{}", queue.poll(2L, TimeUnit.SECONDS));
            //null
            log.info("poll:{}", queue.poll(2L, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        try {
            //正常put
            queue.put("1");
            //正常put
            queue.put("2");
            //正常put
            queue.put("3");
            //put不进去会一直阻塞
            //queue.put("4");
            //1
            log.info("take:{}", queue.take());
            //2
            log.info("take:{}", queue.take());
            //3
            log.info("take:{}", queue.take());
            //take不出来会一直阻塞
            //log.info("take:{}", queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        //true
        log.info("offer:{}", queue.offer("1"));
        //true
        log.info("offer:{}", queue.offer("2"));
        //true
        log.info("offer:{}", queue.offer("3"));
        //false
        log.info("offer:{}", queue.offer("4"));
        //1
        log.info("peek:{}", queue.peek());
        //1
        log.info("poll:{}", queue.poll());
        //2
        log.info("poll:{}", queue.poll());
        //3
        log.info("poll:{}", queue.poll());
        //null
        log.info("poll:{}", queue.poll());
    }

    private static void test() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        //true
        log.info("add:{}", queue.add("1"));
        //true
        log.info("add:{}", queue.add("2"));
        //true
        log.info("add:{}", queue.add("3"));
        //java.lang.IllegalStateException: Queue full
        //log.info("add:{}", queue.add("4"));
        log.info("element:{}", queue.element());
        //1
        log.info("remove:{}", queue.remove());
        //2
        log.info("remove:{}", queue.remove());
        //3
        log.info("remove:{}", queue.remove());
        //java.util.NoSuchElementException
        log.info("remove:{}", queue.remove());
    }
}

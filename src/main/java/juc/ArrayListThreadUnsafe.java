package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 53137
 */
@Slf4j(topic = "c.ArrayListThreadUnsafe")
public class ArrayListThreadUnsafe {
    public static void main(String[] args) throws InterruptedException {
        //test();
        test1();
    }

    /**
     * 出现 java.util.ConcurrentModificationException
     */
    private static void test1() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list.toString());
            }, "t" + i).start();
        }
    }

    /**
     * 正常输出
     */
    private static void test() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list.toString());
    }
}

package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author 53137
 */
@Slf4j(topic = "c.ArrayListThreadUnsafe")
public class ArrayListThreadUnsafe {
    public static void main(String[] args) throws InterruptedException {
        //test();
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        test6();

    }

    /**
     * ConcurrentHashMap解决ConcurrentModificationException
     */
    private static void test6() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map.toString());
            }, "t" + i).start();
        }
    }

    /**
     * CopyOnWriteArraySet解决ConcurrentModificationException
     * 底层CopyOnWriteArrayList
     */
    private static void test5() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set.toString());
            }, "t" + i).start();
        }
    }


    /**
     * 使用CopyOnWriteArrayList解决ConcurrentModificationException
     */
    private static void test4() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list.toString());
            }, "t" + i).start();
        }
    }

    /**
     * Collections.synchronizedList解决ConcurrentModificationException
     */
    private static void test3() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list.toString());
            }, "t" + i).start();
        }
    }

    /**
     * Vector解决ConcurrentModificationException
     */
    private static void test2() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list.toString());
            }, "t" + i).start();
        }
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

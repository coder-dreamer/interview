package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁demo
 *
 * @author 53137
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        //test();
        ReadWriteLockCacheV2 cache = new ReadWriteLockCacheV2();
        //五个线程写
        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                cache.put(index + "", index + "");
            }, "t" + (i + 1)).start();
        }
        //五个线程读
        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                cache.get(index + "");
            }, "t" + (i + 1)).start();
        }
    }

    private static void test() {
        ReadWriteLockCacheV1 cache = new ReadWriteLockCacheV1();
        //五个线程写
        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                cache.put(index + "", index + "");
            }, "t" + (i + 1)).start();
        }
        //五个线程读
        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                cache.get(index + "");
            }, "t" + (i + 1)).start();
        }
    }
}

@Slf4j(topic = "c.ReadWriteLockCacheV1")
class ReadWriteLockCacheV1 {
    private Map<String, String> map = new HashMap<>();

    public void put(String key, String value) {
        log.debug("{}正在写入...", Thread.currentThread().getName());
        map.put(key, value);
        log.debug("{}写入完成...", Thread.currentThread().getName());
    }

    public String get(String key) {
        log.debug("{}正在读取...", Thread.currentThread().getName());
        String value = map.get(key);
        log.debug("{}读取完成...，值{}", Thread.currentThread().getName(), value);
        return value;
    }
}

@Slf4j(topic = "c.ReadWriteLockCacheV2")
class ReadWriteLockCacheV2 {
    private Map<String, String> map = new HashMap<>();
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        rwLock.writeLock().lock();
        try {
            log.debug("{}正在写入...", Thread.currentThread().getName());
            map.put(key, value);
            log.debug("{}写入完成...", Thread.currentThread().getName());
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public String get(String key) {
        rwLock.readLock().lock();
        try {
            log.debug("{}正在读取...", Thread.currentThread().getName());
            String value = map.get(key);
            log.debug("{}读取完成...，值{}", Thread.currentThread().getName(), value);
            return value;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
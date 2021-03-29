package juc;


/**
 * synchronized可重入锁demo
 *
 * @author 53137
 */
public class SynchronizedReenterLockTest {
    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {
        Phone phone = new Phone();
        phone.sendSMS();
    }
}

class Phone {
    /**
     * 发送短信
     *
     * @throws Exception
     */
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");

        // 在同步方法中，调用另外一个同步方法
        sendEmail();
    }

    /**
     * 发邮件
     *
     * @throws Exception
     */
    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }
}
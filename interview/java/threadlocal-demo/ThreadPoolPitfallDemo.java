import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo 2: 线程池场景踩坑演示
 *
 * 验证要点：
 * - 线程池复用线程时，上一个任务的 ThreadLocal 值会残留
 * - 不 remove 会导致数据"串"到别的请求
 * - 加上 remove 后问题消失
 *
 * 运行: javac ThreadPoolPitfallDemo.java && java ThreadPoolPitfallDemo
 */
public class ThreadPoolPitfallDemo {

    private static final ThreadLocal<String> REQUEST_USER = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // 只有 1 个线程的线程池，强制复用同一个线程
        ExecutorService pool = Executors.newFixedThreadPool(1);

        System.out.println("===== 场景1: 不调用 remove（有 Bug）=====");
        System.out.println();

        // 第一个请求：设置了用户，但忘记 remove
        pool.submit(() -> {
            REQUEST_USER.set("张三");
            System.out.println("[请求1] 当前用户 = " + REQUEST_USER.get());
            // 忘记 remove 了！
        });
        Thread.sleep(100);

        // 第二个请求：没有 set，但却能读到上一个请求的值！
        pool.submit(() -> {
            System.out.println("[请求2] 当前用户 = " + REQUEST_USER.get()); // 张三！数据串了！
            System.out.println("  ↑ BUG! 请求2没有设置用户，却读到了张三的数据！");
            REQUEST_USER.remove(); // 清理掉
        });
        Thread.sleep(100);

        System.out.println();
        System.out.println("===== 场景2: 正确使用 try-finally + remove =====");
        System.out.println();

        // 第一个请求：正确使用
        pool.submit(() -> {
            try {
                REQUEST_USER.set("李四");
                System.out.println("[请求3] 当前用户 = " + REQUEST_USER.get());
                // 模拟业务逻辑
            } finally {
                REQUEST_USER.remove(); // 一定要清理
            }
        });
        Thread.sleep(100);

        // 第二个请求：读不到残留数据
        pool.submit(() -> {
            System.out.println("[请求4] 当前用户 = " + REQUEST_USER.get()); // null，正确！
            System.out.println("  ↑ 正确! 读到 null，没有数据残留");
        });
        Thread.sleep(100);

        pool.shutdown();

        System.out.println();
        System.out.println("=== 结论：线程池中使用 ThreadLocal 必须在 finally 里 remove ===");
    }
}

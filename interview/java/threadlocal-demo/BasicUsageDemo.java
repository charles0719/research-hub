/**
 * Demo 1: ThreadLocal 基本用法 & 线程隔离验证
 *
 * 验证要点：
 * - 同一个 ThreadLocal 对象，不同线程 set 的值互不影响
 * - 每个线程看到的都是自己的那份数据
 *
 * 运行: javac BasicUsageDemo.java && java BasicUsageDemo
 */
public class BasicUsageDemo {

    private static final ThreadLocal<String> USER_CONTEXT = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // 主线程设置值
        USER_CONTEXT.set("主线程用户");
        System.out.println("[主线程] 设置值 = " + USER_CONTEXT.get());

        // 线程 A
        Thread threadA = new Thread(() -> {
            USER_CONTEXT.set("线程A用户");
            System.out.println("[线程A] 设置值 = " + USER_CONTEXT.get());
            sleep(100); // 等一下，确保线程B已经改了值
            System.out.println("[线程A] 再次读取 = " + USER_CONTEXT.get()); // 依然是自己的值
            USER_CONTEXT.remove();
        }, "Thread-A");

        // 线程 B
        Thread threadB = new Thread(() -> {
            USER_CONTEXT.set("线程B用户");
            System.out.println("[线程B] 设置值 = " + USER_CONTEXT.get());
            USER_CONTEXT.remove();
        }, "Thread-B");

        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        // 主线程的值不受影响
        System.out.println("[主线程] 最终值 = " + USER_CONTEXT.get()); // 依然是 "主线程用户"
        USER_CONTEXT.remove();

        System.out.println();
        System.out.println("=== 结论：各线程的值完全隔离，互不干扰 ===");
    }

    private static void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}

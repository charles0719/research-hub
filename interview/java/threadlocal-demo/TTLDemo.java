import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo: InheritableThreadLocal vs TransmittableThreadLocal 在线程池中的表现
 *
 * 验证要点：
 * - InheritableThreadLocal 在线程池中失效（只在 new Thread 时拷贝，线程复用不会再拷贝）
 * - TransmittableThreadLocal 在线程池中正常传递（每次提交任务时捕获快照）
 *
 * 运行（需要 TTL 依赖）:
 *   mvn compile exec:java -Dexec.mainClass="TTLDemo"
 *   或者手动指定 classpath:
 *   javac -cp transmittable-thread-local-x.x.x.jar TTLDemo.java
 *   java -cp .:transmittable-thread-local-x.x.x.jar TTLDemo
 */
public class TTLDemo {

    // JDK 原生
    private static final InheritableThreadLocal<String> ITL = new InheritableThreadLocal<>();
    // 阿里 TTL
    private static final TransmittableThreadLocal<String> TTL = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // 只有 1 个线程的线程池，确保线程被复用
        ExecutorService rawPool = Executors.newFixedThreadPool(1);
        // 用 TtlExecutors 包装线程池，TTL 才能生效
        ExecutorService ttlPool = TtlExecutors.getTtlExecutorService(rawPool);

        // ===== 第一轮：先让线程池的线程创建出来 =====
        ITL.set("初始值");
        TTL.set("初始值");

        rawPool.submit(() -> {
            // 线程池的线程在这里被创建，会拷贝 InheritableThreadLocal 的值
            System.out.println("[第1轮-线程池线程创建] ITL = " + ITL.get()); // "初始值"
        });
        Thread.sleep(200);

        // ===== 第二轮：父线程修改值后再提交任务 =====
        System.out.println();
        System.out.println("===== 父线程修改值为\"请求A的用户\" =====");
        ITL.set("请求A的用户");
        TTL.set("请求A的用户");

        // InheritableThreadLocal —— 用原始线程池
        rawPool.submit(() -> {
            System.out.println("[InheritableThreadLocal] 期望\"请求A的用户\"，实际 = " + ITL.get());
            System.out.println("  ↑ 失效！拿到的还是线程创建时的旧值，因为线程是复用的，不会重新拷贝");
        });
        Thread.sleep(200);

        // TransmittableThreadLocal —— 用 TTL 包装的线程池
        ttlPool.submit(() -> {
            System.out.println("[TransmittableThreadLocal] 期望\"请求A的用户\"，实际 = " + TTL.get());
            System.out.println("  ↑ 成功！每次提交任务时都会重新捕获父线程的值");
        });
        Thread.sleep(200);

        // ===== 第三轮：再改一次值，验证每次提交都能传递最新值 =====
        System.out.println();
        System.out.println("===== 父线程修改值为\"请求B的用户\" =====");
        ITL.set("请求B的用户");
        TTL.set("请求B的用户");

        rawPool.submit(() -> {
            System.out.println("[InheritableThreadLocal] 期望\"请求B的用户\"，实际 = " + ITL.get());
            System.out.println("  ↑ 依然失效");
        });
        Thread.sleep(200);

        ttlPool.submit(() -> {
            System.out.println("[TransmittableThreadLocal] 期望\"请求B的用户\"，实际 = " + TTL.get());
            System.out.println("  ↑ 依然成功");
        });
        Thread.sleep(200);

        rawPool.shutdown();

        System.out.println();
        System.out.println("=== 结论 ===");
        System.out.println("InheritableThreadLocal：只在 new Thread() 时拷贝一次，线程池复用线程不会再拷贝 → 失效");
        System.out.println("TransmittableThreadLocal：每次提交任务时捕获快照 → 线程池场景正常工作");
    }
}

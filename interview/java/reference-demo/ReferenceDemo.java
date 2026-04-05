import java.lang.ref.*;

/**
 * Java 四种引用类型完整演示：强引用、软引用、弱引用、虚引用
 *
 * 运行: javac ReferenceDemo.java && java -Xmx20m ReferenceDemo
 *       ↑ 限制堆内存 20MB，方便观察软引用在内存不足时被回收
 */
public class ReferenceDemo {

    // 用来跟踪对象被回收的时机
    private static final ReferenceQueue<byte[]> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {
        strongReferenceDemo();
        System.out.println();
        weakReferenceDemo();
        System.out.println();
        softReferenceDemo();
        System.out.println();
        phantomReferenceDemo();
    }

    // ========== 1. 强引用 ==========
    static void strongReferenceDemo() {
        System.out.println("===== 1. 强引用（Strong Reference）=====");
        System.out.println("特点：只要强引用在，GC 永远不会回收，宁可 OOM");
        System.out.println();

        Object obj = new Object();  // obj 就是强引用
        System.out.println("GC 前: obj = " + obj);

        System.gc();
        sleep(100);
        System.out.println("GC 后: obj = " + obj);  // 依然存在
        System.out.println("  → 强引用存在，GC 不会回收");

        obj = null;  // 断开强引用
        System.gc();
        sleep(100);
        System.out.println("obj=null 后 GC: 对象已被回收（无法再访问）");
        System.out.println("  → 强引用断开后，对象才能被回收");
    }

    // ========== 2. 弱引用 ==========
    static void weakReferenceDemo() {
        System.out.println("===== 2. 弱引用（Weak Reference）=====");
        System.out.println("特点：无论内存是否充足，下次 GC 直接回收");
        System.out.println("应用：ThreadLocalMap 的 Entry key");
        System.out.println();

        // 场景1：有强引用兜底
        Object obj = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(obj);

        System.gc();
        sleep(100);
        System.out.println("强引用存在时 GC: weakRef.get() = " + weakRef.get());
        System.out.println("  → 强引用在，弱引用也能拿到对象");

        // 场景2：去掉强引用
        obj = null;
        System.gc();
        sleep(100);
        System.out.println("强引用断开后 GC: weakRef.get() = " + weakRef.get());
        System.out.println("  → 只剩弱引用，GC 直接回收，get() 返回 null");
    }

    // ========== 3. 软引用 ==========
    static void softReferenceDemo() {
        System.out.println("===== 3. 软引用（Soft Reference）=====");
        System.out.println("特点：内存充足时不回收，内存不足时才回收");
        System.out.println("应用：缓存（图片缓存、网页缓存）");
        System.out.println();

        // 创建一个 5MB 的软引用对象（运行时请加 -Xmx20m）
        SoftReference<byte[]> softRef = new SoftReference<>(new byte[5 * 1024 * 1024]);

        System.out.println("刚创建: softRef.get() 是否为 null? " + (softRef.get() == null));

        System.gc();
        sleep(100);
        System.out.println("内存充足时 GC: softRef.get() 是否为 null? " + (softRef.get() == null));
        System.out.println("  → 内存够用，GC 不会回收软引用");

        // 尝试分配大量内存，制造内存压力
        System.out.println();
        System.out.println("制造内存压力（连续分配大数组）...");
        try {
            byte[][] pressure = new byte[5][];
            for (int i = 0; i < 5; i++) {
                pressure[i] = new byte[3 * 1024 * 1024]; // 每次 3MB
            }
        } catch (OutOfMemoryError e) {
            // 预期会 OOM，忽略
        }

        System.out.println("内存不足后: softRef.get() 是否为 null? " + (softRef.get() == null));
        System.out.println("  → 内存不足时，JVM 会回收软引用对象来腾空间");
        System.out.println("  （结果取决于 JVM 具体策略和 -Xmx 设置，可能为 true 或 false）");
    }

    // ========== 4. 虚引用 ==========
    static void phantomReferenceDemo() {
        System.out.println("===== 4. 虚引用（Phantom Reference）=====");
        System.out.println("特点：get() 永远返回 null，只能通过 ReferenceQueue 感知对象被回收");
        System.out.println("应用：DirectByteBuffer 的 Cleaner 释放堆外内存");
        System.out.println();

        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object obj = new Object();
        PhantomReference<Object> phantomRef = new PhantomReference<>(obj, queue);

        // get() 永远是 null
        System.out.println("phantomRef.get() = " + phantomRef.get());
        System.out.println("  → 虚引用的 get() 永远返回 null，拿不到对象");

        // 回收前，队列里没东西
        System.out.println("GC 前 queue.poll() = " + queue.poll());
        System.out.println("  → 对象还没被回收，队列为空");

        // 去掉强引用，触发 GC
        obj = null;
        System.gc();
        sleep(200);

        // 回收后，虚引用被放入队列
        Reference<?> ref = queue.poll();
        System.out.println("GC 后 queue.poll() = " + ref);
        System.out.println("  → 对象被回收后，虚引用入队，程序可以据此做清理工作");
        System.out.println("  → 比如 Netty 用这个机制追踪 ByteBuf 是否被正确释放");
    }

    private static void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}

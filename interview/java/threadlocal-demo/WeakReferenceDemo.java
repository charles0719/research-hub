import java.lang.ref.WeakReference;

/**
 * Demo 4: 弱引用机制直观感受
 *
 * 验证要点：
 * - 强引用：GC 不会回收
 * - 弱引用：GC 时直接回收
 * - 帮助理解 ThreadLocalMap 中 Entry 的 key 为什么是弱引用
 *
 * 运行: javac WeakReferenceDemo.java && java WeakReferenceDemo
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {

        System.out.println("===== 弱引用 vs 强引用 =====");
        System.out.println();

        // 1. 强引用 —— GC 不会回收
        Object strongObj = new Object();
        WeakReference<Object> weakRef1 = new WeakReference<>(strongObj);

        System.gc();
        System.out.println("[强引用存在时] weakRef.get() = " + weakRef1.get()); // 不为 null

        // 2. 去掉强引用 —— GC 回收
        strongObj = null;
        System.gc();
        // 给 GC 一点时间
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        System.out.println("[强引用去掉后] weakRef.get() = " + weakRef1.get()); // null，已被回收

        System.out.println();
        System.out.println("===== 类比 ThreadLocalMap.Entry =====");
        System.out.println();
        System.out.println("ThreadLocalMap.Entry 继承 WeakReference<ThreadLocal<?>>：");
        System.out.println("  - key（ThreadLocal）是弱引用 → 外部无强引用时可被 GC");
        System.out.println("  - value 是强引用 → 即使 key 被回收，value 仍然存在");
        System.out.println("  - 所以必须手动 remove()，否则 value 就泄漏了");
    }
}

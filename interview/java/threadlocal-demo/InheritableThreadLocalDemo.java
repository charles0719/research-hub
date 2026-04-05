/**
 * Demo 3: InheritableThreadLocal 父子线程传值
 *
 * 验证要点：
 * - 普通 ThreadLocal 子线程拿不到父线程的值
 * - InheritableThreadLocal 子线程创建时会拷贝父线程的值
 * - 但拷贝只发生在创建时，后续修改互不影响
 *
 * 运行: javac InheritableThreadLocalDemo.java && java InheritableThreadLocalDemo
 */
public class InheritableThreadLocalDemo {

    private static final ThreadLocal<String> NORMAL = new ThreadLocal<>();
    private static final InheritableThreadLocal<String> INHERITABLE = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // 父线程设置值
        NORMAL.set("普通值");
        INHERITABLE.set("可继承值");

        System.out.println("[父线程] NORMAL      = " + NORMAL.get());
        System.out.println("[父线程] INHERITABLE = " + INHERITABLE.get());
        System.out.println();

        // 创建子线程
        Thread child = new Thread(() -> {
            System.out.println("[子线程] NORMAL      = " + NORMAL.get());      // null
            System.out.println("[子线程] INHERITABLE = " + INHERITABLE.get());  // "可继承值"
            System.out.println();

            // 子线程修改值
            INHERITABLE.set("子线程修改后的值");
            System.out.println("[子线程] 修改后 INHERITABLE = " + INHERITABLE.get());
        }, "Child");

        child.start();
        child.join();

        // 父线程的值不受子线程修改的影响
        System.out.println();
        System.out.println("[父线程] 子线程结束后 INHERITABLE = " + INHERITABLE.get()); // 仍然是原值
        System.out.println();
        System.out.println("=== 结论 ===");
        System.out.println("1. 普通 ThreadLocal：子线程读不到父线程的值");
        System.out.println("2. InheritableThreadLocal：子线程创建时拷贝父线程的值");
        System.out.println("3. 拷贝是一次性的，后续修改互不影响");

        NORMAL.remove();
        INHERITABLE.remove();
    }
}

# ThreadLocal Demo

面试知识点验证，配合 [面试题笔记](../../../../求职/面试/面试题/Java/ThreadLocal.md) 食用。

## Demo 列表

| 文件 | 验证内容 |
|---|---|
| `BasicUsageDemo.java` | 基本用法，验证线程间数据隔离 |
| `ThreadPoolPitfallDemo.java` | 线程池中不 remove 导致数据串线程（最常见的坑） |
| `InheritableThreadLocalDemo.java` | 父子线程传值，验证 InheritableThreadLocal 行为 |
| `WeakReferenceDemo.java` | 弱引用机制，帮助理解内存泄漏原理 |
| `TTLDemo.java` | InheritableThreadLocal vs TransmittableThreadLocal（阿里 TTL）在线程池中的对比 |

## 运行方式

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/interview/java/threadlocal-demo

# 不需要依赖的 demo，直接 javac + java
javac BasicUsageDemo.java && java BasicUsageDemo
javac ThreadPoolPitfallDemo.java && java ThreadPoolPitfallDemo
javac InheritableThreadLocalDemo.java && java InheritableThreadLocalDemo
javac WeakReferenceDemo.java && java WeakReferenceDemo

# TTL demo 需要阿里的依赖，通过 Maven 运行
mvn compile org.codehaus.mojo:exec-maven-plugin:3.1.0:java -Dexec.mainClass="TTLDemo"
```

## 建议实验顺序

1. **BasicUsageDemo** — 先跑通基本用法，观察隔离效果
2. **ThreadPoolPitfallDemo** — 重点！面试必问的线程池踩坑场景
3. **InheritableThreadLocalDemo** — 了解父子线程传值机制
4. **TTLDemo** — InheritableThreadLocal 在线程池失效 vs TTL 正常工作的对比
5. **WeakReferenceDemo** — 理解底层弱引用，搞懂内存泄漏的根因

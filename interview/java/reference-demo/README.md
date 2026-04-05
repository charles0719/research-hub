# Java 四种引用类型 Demo

面试知识点验证，配合 [面试题笔记](../../../../求职/面试/面试题/Java/四种引用类型.md) 食用。

## 运行方式

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/interview/java/reference-demo
javac ReferenceDemo.java && java -Xmx20m ReferenceDemo
```

> `-Xmx20m` 限制堆内存为 20MB，方便观察软引用在内存不足时被回收的效果。

## 覆盖内容

| 引用类型 | 验证点 |
|---|---|
| 强引用 | GC 不会回收，只有断开引用（= null）后才能被回收 |
| 弱引用 | 无论内存是否充足，下次 GC 直接回收 |
| 软引用 | 内存充足时不回收，制造内存压力后被回收 |
| 虚引用 | get() 永远返回 null，通过 ReferenceQueue 感知对象被回收 |

package com.example.taskframework.executor;
import com.example.taskframework.task.Task;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 1L;
    private static final int QUEUE_CAPACITY = 10;
    private final ThreadPoolExecutor threadPool;
    public TaskExecutor() {
        threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                new CustomThreadFactory("task-thread-%d"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
    public void submitTask(Task task) {
        threadPool.execute(() -> {
            System.out.printf("提交任务：%s（线程：%s）%n", task.getTaskName(), Thread.currentThread().getName());
            boolean success = task.execute();
            System.out.printf("任务完成：%s（状态：%s）%n", task.getTaskName(), success ? "成功" : "失败");
        });
    }
    public void shutdown() {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) { threadPool.shutdownNow(); }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("线程池已关闭");
    }
    public int getActiveThreadCount() { return threadPool.getActiveCount(); }
    public int getQueueSize() { return threadPool.getQueue().size(); }
    private static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String nameFormat;
        public CustomThreadFactory(String nameFormat) { this.nameFormat = nameFormat; }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, String.format(nameFormat, threadNumber.getAndIncrement()));
            t.setDaemon(false); // 非守护线程，确保任务执行完成
            return t;
        }
    }
}
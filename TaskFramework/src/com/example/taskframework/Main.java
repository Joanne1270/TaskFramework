package com.example.taskframework;
import com.example.taskframework.executor.TaskExecutor;
import com.example.taskframework.task.Task;
import com.example.taskframework.util.TaskLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 多线程任务调度框架启动 ===");
        TaskExecutor executor = new TaskExecutor();
        try {
            Task printTask1 = TaskLoader.loadTask("PrintTask", "Hello, 反射+多线程！");
            Task printTask2 = TaskLoader.loadTask("PrintTask", "第二个打印任务");
            Task calcTask1 = TaskLoader.loadTask("CalcTask", 10);
            Task calcTask2 = TaskLoader.loadTask("CalcTask", 20);
            Task delayTask1 = TaskLoader.loadTask("DelayTask", 1000L);
            Task delayTask2 = TaskLoader.loadTask("DelayTask", 2000L);

            executor.submitTask(printTask1);
            executor.submitTask(printTask2);
            executor.submitTask(calcTask1);
            executor.submitTask(calcTask2);
            executor.submitTask(delayTask1);
            executor.submitTask(delayTask2);

            Thread.sleep(500);
            System.out.printf("当前活跃线程数：%d，队列任务数：%d%n",
                    executor.getActiveThreadCount(), executor.getQueueSize());

            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("任务加载或执行失败：" + e.getMessage());
            e.printStackTrace();
        } finally { executor.shutdown(); }

        System.out.println("=== 所有任务执行完成 ===");
    }
}
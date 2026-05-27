package com.example.taskframework.task;
public class DelayTask implements Task {
    private final long delayMs;
    public DelayTask(long delayMs) { this.delayMs = delayMs; }

    @Override
    public boolean execute() {
        try {
            Thread.sleep(delayMs);
            System.out.printf("[线程：%s] 延迟任务完成（延迟%dms）%n", Thread.currentThread().getName(), delayMs);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("[线程：%s] 延迟任务被中断：%s%n", Thread.currentThread().getName(), e.getMessage());
            return false;
        }
    }

    @Override
    public String getTaskName() { return "DelayTask"; }
}
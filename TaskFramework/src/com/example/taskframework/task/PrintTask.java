package com.example.taskframework.task;
public class PrintTask implements Task {
    private final String message;
    public PrintTask(String message) { this.message = message; }

    @Override
    public boolean execute() {
        System.out.printf("[线程：%s] 打印任务执行：%s%n", Thread.currentThread().getName(), message);
        return true;
    }

    @Override
    public String getTaskName() { return "PrintTask"; }
}
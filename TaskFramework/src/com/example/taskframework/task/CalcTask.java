package com.example.taskframework.task;
public class CalcTask implements Task {
    private final int num;
    public CalcTask(int num) { this.num = num; }

    @Override
    public boolean execute() {
        int result = num * num;
        System.out.printf("[线程：%s] 计算任务执行：%d 的平方 = %d%n", Thread.currentThread().getName(), num, result);
        return true;
    }

    @Override
    public String getTaskName() { return "CalcTask"; }
}
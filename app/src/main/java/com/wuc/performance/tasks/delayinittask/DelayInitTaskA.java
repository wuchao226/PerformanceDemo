package com.wuc.performance.tasks.delayinittask;


import com.wuc.performance.launcherstarter.task.MainTask;
import com.wuc.performance.utils.LogUtils;

public class DelayInitTaskA extends MainTask {

    @Override
    public void run() {
        // 模拟一些操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i("DelayInitTaskA finished");
    }
}

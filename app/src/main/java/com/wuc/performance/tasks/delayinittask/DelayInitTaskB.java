package com.wuc.performance.tasks.delayinittask;


import com.wuc.performance.launcherstarter.task.MainTask;
import com.wuc.performance.utils.LogUtils;

public class DelayInitTaskB extends MainTask {

    @Override
    public void run() {
        // 模拟一些操作

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i("DelayInitTaskB finished");
    }
}

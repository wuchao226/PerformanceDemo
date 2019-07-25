package com.wuc.performance.tasks;


import cn.jpush.android.api.JPushInterface;
import com.wuc.performance.PerformanceApp;
import com.wuc.performance.launcherstarter.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要在getDeviceId之后执行
 */
public class InitJPushTask extends Task {
    /**
     * 依赖关系
     * @return
     */
    @Override
    public List<Class<? extends Task>> dependsOn() {
        List<Class<? extends Task>> task = new ArrayList<>();
        task.add(GetDeviceIdTask.class);
        return task;
    }

    @Override
    public void run() {
        JPushInterface.init(mContext);
        PerformanceApp app = (PerformanceApp) mContext;
        JPushInterface.setAlias(mContext, 0, app.getDeviceId());
    }
}

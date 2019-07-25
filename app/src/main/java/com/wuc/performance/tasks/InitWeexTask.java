package com.wuc.performance.tasks;


import android.app.Application;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.wuc.performance.launcherstarter.task.MainTask;

/**
 * 主线程执行的task(需要在onCreate()方法中执行完成)
 */
public class InitWeexTask extends MainTask {
    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     * @return
     */
    @Override
    public boolean needWait() {
        return true;
    }

    @Override
    public void run() {
        InitConfig config = new InitConfig.Builder().build();
        WXSDKEngine.initialize((Application) mContext, config);
    }
}

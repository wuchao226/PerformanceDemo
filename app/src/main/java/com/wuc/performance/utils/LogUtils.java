package com.wuc.performance.utils;


import android.util.Log;
import com.wuc.performance.PerformanceApp;

import java.util.concurrent.ExecutorService;

public class LogUtils {

    public static final String TAG = "performance";
    private static ExecutorService sExecutorService;

    public static void setExecutor(ExecutorService executorService) {
        sExecutorService = executorService;
    }

    public static void i(String msg) {
        if (Utils.isMainProcess(PerformanceApp.Companion.getApplication())) {
            Log.i(TAG, msg);
        }
        // 异步
        if (sExecutorService != null) {
//            sExecutorService.execute();
        }
    }

}

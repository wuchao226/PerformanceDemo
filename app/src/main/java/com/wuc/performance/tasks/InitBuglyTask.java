package com.wuc.performance.tasks;


import com.tencent.bugly.crashreport.CrashReport;
import com.wuc.performance.launcherstarter.task.Task;

public class InitBuglyTask extends Task {

    @Override
    public void run() {
        CrashReport.initCrashReport(mContext, "注册时申请的APPID", false);
    }
}

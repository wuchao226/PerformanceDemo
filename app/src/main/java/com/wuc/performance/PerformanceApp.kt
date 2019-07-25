package com.wuc.performance

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import cn.jpush.android.api.JPushInterface
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.commonsdk.UMConfigure
import com.wuc.performance.launcherstarter.TaskDispatcher
import com.wuc.performance.tasks.*
import com.wuc.performance.utils.LaunchTimer
import java.util.concurrent.CountDownLatch


/**
 * @author:     wuchao
 * @date:       2019-07-02 18:11
 * @desciption:
 */
class PerformanceApp : Application() {

    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    private val mLocationListener = AMapLocationListener {
        // 一些处理
    }

    fun setDeviceId(deviceId: String) {
        this.mDeviceId = deviceId
    }

    fun getDeviceId(): String? {
        return mDeviceId
    }

    private var mDeviceId: String? = null

    companion object {
        private var sApplication: Application? = null
        fun getApplication(): Application? {
            return sApplication
        }
    }

    //参考 AsyncTask 源码中线程池设置的核心数，根据 CPU 的核心数确定线程池的数量
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4))

    private val mCountDownLatch = CountDownLatch(1)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //开始时间的记录
        LaunchTimer.startRecord()
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        LaunchTimer.startRecord()
        TaskDispatcher.init(this)
        val dispatcher = TaskDispatcher.createInstance()
        dispatcher.addTask(InitAMapTask())
            .addTask(InitStethoTask())
            .addTask(InitWeexTask())
            .addTask(InitBuglyTask())
            .addTask(InitFrescoTask())
            .addTask(InitJPushTask())
            .addTask(InitUmengTask())
            .addTask(GetDeviceIdTask())
            .start()
        //启动器中配置需要等待的函数没有完成是都会等待
        dispatcher.await()
        LaunchTimer.endRecord("task")
        //TraceCompat.beginSection("ApponCreate")
        //Debug.startMethodTracing("PerformanceApp")
        //LaunchTimer.startRecord()
        /* val service = Executors.newFixedThreadPool(CORE_POOL_SIZE)
         service.submit {
             initBugly()
         }
         service.submit {
             initUmeng()
         }
         service.submit {
             initFresco()
         }
         service.submit {
             initMap()
             mCountDownLatch.countDown()
         }*/
        /*initBugly()
        initUmeng()
        initFresco()
        initMap()*/
        //CountDownLatch不被满足的话会一直等待
        // mCountDownLatch.await()
        //LaunchTimer.endRecord("PerformApp")

        //TraceCompat.endSection()
        //Debug.stopMethodTracing()
    }

    //高德地图
    private fun initMap() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient!!.setLocationListener(mLocationListener)
        mLocationOption = AMapLocationClientOption()
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption!!.isOnceLocation = true
        mLocationClient!!.setLocationOption(mLocationOption)
        mLocationClient!!.startLocation()
    }

    private fun initBugly() {
        CrashReport.initCrashReport(applicationContext, "401703228b", true)
    }

    private fun initUmeng() {
        UMConfigure.init(
            this, "5d1da3103fc1950df5001073", "umeng",
            UMConfigure.DEVICE_TYPE_PHONE, ""
        )
    }

    private fun initJPush() {
        JPushInterface.init(this)
        JPushInterface.setAlias(this, 0, mDeviceId)
    }

    private fun initFresco() {
        Fresco.initialize(this)
    }
}
package com.wuc.performance.utils

/**
 * @author:     wuchao
 * @date:       2019-07-03 13:36
 * @desciption:
 */
class LaunchTimer {
    companion object {
        private var time: Long = 0L
        /**
         * 记录开始计时时间
         */
        fun startRecord() {
            time = System.currentTimeMillis()
        }

        /**
         * 记录启动结束的计时时间
         */
        fun endRecord() {
            endRecord("")
        }

        fun endRecord(msg: String) {
            val cost = System.currentTimeMillis() - time
            LogUtils.i("$msg cost $cost")
        }
    }
}
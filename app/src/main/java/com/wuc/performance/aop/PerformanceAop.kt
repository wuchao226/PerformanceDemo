package com.wuc.performance.aop

import com.wuc.performance.utils.LogUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * @author:     wuchao
 * @date:       2019-07-04 17:18
 * @desciption:
 */
@Aspect
class PerformanceAop {
    @Around("call(* com.wuc.performance.PerformanceApp.**(..)")
    fun getTime(joinPoint: ProceedingJoinPoint) {
        val time = System.currentTimeMillis()
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        LogUtils.i("cost " + (System.currentTimeMillis() - time))
    }
}
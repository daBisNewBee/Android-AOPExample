package org.android10.gintonic.aspect;

import android.os.Build;
import android.os.Trace;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * "对 App 中所有的方法进行 Systrace 函数插桩"
 *
 * 这就是一个"切面文件"，它被用来监控。。。。。
 */
@Aspect
public class SystraceAspect {

    // 定义一个切入点方法 baseCondition，用于排除 component 中相应的类、Boy的监控
    @Pointcut("!within(org.android10.viewgroupperformance.activity.Boy) " +
            "&& !within(org.android10.viewgroupperformance.component.*)")
    public void baseCondition(){

    }

    // 前置通知，监测所有函数，并排除Boy、component下所有方法的前置通知
    @Before("execution(* **(..)) && baseCondition()")
    public void beforexxx(JoinPoint joinPoint){
        Log.d("Gintonic", joinPoint.getSignature().toShortString() + " 开始监测...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection(joinPoint.getSignature().toString());
        }
    }

    // 后置通知
    @After("execution(* **(..))")
    public void afterxxx(JoinPoint joinPoint){
        Log.d("Gintonic", joinPoint.getSignature().toShortString() + " 结束监测...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }
    }
}

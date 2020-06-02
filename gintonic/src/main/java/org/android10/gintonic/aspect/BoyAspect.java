package org.android10.gintonic.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * "统计Boy下所有函数的耗时"
 *
 * ex：
 * Boy.myFunc() cost：301
 * Boy.fuckYou() cost：302
 *
 *
 * "execution":代表方法执行的位置，插入在函数体"内部"。
 *
 *     public static void fuckYou() {
 *         JoinPoint var0 = Factory.makeJP(ajc$tjp_0, (Object)null, (Object)null);
 *         BoyAspect var10000 = BoyAspect.aspectOf();
 *         Object[] var1 = new Object[]{var0};
 *         var10000.getTime((new Boy$AjcClosure1(var1)).linkClosureAndJoinPoint(65536));
 *     }
 *
 *     private static void myFunc() {
 *         JoinPoint var1 = Factory.makeJP(ajc$tjp_1, (Object)null, (Object)null);
 *         BoyAspect var10000 = BoyAspect.aspectOf();
 *         Object[] var2 = new Object[]{var1};
 *         var10000.getTime((new Boy$AjcClosure3(var2)).linkClosureAndJoinPoint(65536));
 *     }
 *
 *     static {
 *         ajc$preClinit();
 *     }
 *
 *  "call":代表调用方法的位置，插入在函数体"外面"。
 *
 *      public static void fuckYou() {
 *         JoinPoint var0 = Factory.makeJP(ajc$tjp_0, (Object)null, (Object)null);
 *         BoyAspect var10000 = BoyAspect.aspectOf();
 *         Object[] var1 = new Object[]{var0};
 *         var10000.getTime((new Boy$AjcClosure1(var1)).linkClosureAndJoinPoint(0));
 *     }
 *
 *     private static void myFunc() {
 *         try {
 *             Thread.sleep(300L);
 *         } catch (InterruptedException var1) {
 *             var1.printStackTrace();
 *         }
 *
 *     }
 *
 *     static {
 *         ajc$preClinit();
 *     }
 *
 *  "call 与 execution 区别"：
 *    当 call 捕获 joinPoint 时，捕获的签名方法的调用点;execution 捕获 joinPoint 时，捕获的则是执行点。
 *    两个的区别在于一个是 ”调用点“， 一个是 ”执行点“
 *
 */
@Aspect
public class BoyAspect {

    // 环绕通知
    @Around("call (* org.android10.viewgroupperformance.activity.Boy.**(..))")
//    @Around("execution (* org.android10.viewgroupperformance.activity.Boy.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.toShortString();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Log.i("Gintonic", name + " cost：" + (System.currentTimeMillis() - time));
    }

}

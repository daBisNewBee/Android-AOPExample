package org.android10.gintonic.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * "在 App 中所有 Activity 中以 on 开头的方法中输出一句 log"
 *
 * 结果：
 *
    MainActivity.onCreate(..) 这里先于 On** 执行
    MainActivity onCreate() called with: savedInstanceState = [null]
    MainActivity.onCreate(..) 这里后于 On** 执行

    MainActivity.onResume() 这里先于 On** 执行
    MainActivity onResume() called
    MainActivity.onResume() 这里后于 On** 执行
 *
 */
@Aspect
public class ActivityAspect {

    @Before("execution(* android.app.Activity.on**(..))")
    public void onActivityCalled111(JoinPoint joinPoint) throws Throwable {
        Log.d("Gintonic", joinPoint.getSignature().toShortString() + " 这里先于 On** 执行");
    }

    @After("execution(* android.app.Activity.on**(..))")
    public void onActivityCalled222(JoinPoint joinPoint) throws Throwable {
        Log.d("Gintonic", joinPoint.getSignature().toShortString() + " 这里后于 On** 执行");

    }
}

/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.viewgroupperformance.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import org.android10.gintonic.annotation.DebugTrace;

/**
 * 插桩技术 AspectJ:
 *
 * 插桩,就是在代码"编译期间"修改已有的代码(一般是Java字节码)获得生成新代码
 *
 *
 * 几点总结：
 *
 * 1. 需要关注下 MyLinearLayout.java、build下的MyLinearLayout.class 和 apk下的MyLinearLayout.class 的区别
 *
 * 2.AspectJ 缺点：
 *    切入点固定
 *    正则表达式固有的缺陷导致的使用不灵活
 *    它还生成了比较多的包装代码
 *
 * 3. AspectJ 的升级、替代方案：
 *  既能够在使用上更加地灵活，也能够避免生成包装代码，以减少插桩所带来的性能损耗呢？
 *  没错，就是 ASM，但是它 需要通过操作 JVM 字节码的方式来进行代码插桩，入手难度比较大
 *
 * 一个简单的插桩流程记录：
 * 1. MyLinearLayout.java
 *   @DebugTrace
 *   @Override
 *   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 *     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
 *     sleep(10);
 *   }
 *
 * 2. MyLinearLayout.class(build下)
 *    @DebugTrace
 *    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 *        JoinPoint var5 = Factory.makeJP(ajc$tjp_0, this, this, Conversions.intObject(widthMeasureSpec), Conversions.intObject(heightMeasureSpec));
 *        TraceAspect var10000 = TraceAspect.aspectOf();
 *        Object[] var6 = new Object[]{this, Conversions.intObject(widthMeasureSpec), Conversions.intObject(heightMeasureSpec), var5};
 *        var10000.weaveJoinPoint((new MyLinearLayout$AjcClosure1(var6)).linkClosureAndJoinPoint(69648));
 *    }
 *
 * 3. MyLinearLayout.class(apk下)
 *    与"2"一样，但是还多了一些方法：
 *    onMeasure_aroundBody0
 *    ajc$preClinit()
 *    等
 *
 */
public class MyLinearLayout extends LinearLayout {

  public MyLinearLayout(Context context) {
    super(context);
  }

  public MyLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @DebugTrace
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    sleep(10);
  }

  @DebugTrace
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    sleep(20);
  }

  @DebugTrace
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    sleep(30);
  }

  /**
   * Method for sleeping. Testing purpose. DON'T DO SOMETHING LIKE THIS!!!
   *
   * @param millis Amount of millis to sleep.
   */
  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

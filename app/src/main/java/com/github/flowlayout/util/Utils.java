package com.github.flowlayout.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.github.flowlayout.BaseApplication;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：FlowLayout
 * Package_Name：com.github.flowlayout.util
 * Version：1.0
 * time：2017/2/15 18:44
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Utils {

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /** 获取主线程的handler */
    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /** 延时在主线程执行runnable */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /** 在主线程执行runnable */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /** 从主线程looper里面移除runnable */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId){
        return LayoutInflater.from(getContext()).inflate(resId,null);
    }

    /** 获取资源 */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /** 获取文字 */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /** 获取文字数组 */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /** 获取dimen */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /** 获取drawable */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /** 获取颜色 */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /** 获取颜色选择器 */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }
    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

}

package com.step.toolkit.util.appinfo

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.text.TextUtils
import java.lang.reflect.Method


object AppInfoUtils {
    private var currentProcessName: String? = null

    fun getCurrentProcessName(context: Context): String? {
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //1)通过Application的API获取当前进程名
        currentProcessName =
            getCurrentProcessNameByApplication();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //2)通过反射ActivityThread获取当前进程名
        currentProcessName =
            getCurrentProcessNameByActivityThread();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        //3)通过ActivityManager获取当前进程名
        currentProcessName =
            getCurrentProcessNameByActivityManager(
                context
            );

        return currentProcessName;
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    fun getCurrentProcessNameByApplication(): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName()
        }
        return null;
    }


    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    fun getCurrentProcessNameByActivityThread(): String? {

        try {
            val declaredMethod: Method = Class.forName(
                "android.app.ActivityThread", false,
                Application::class.java.classLoader
            ).getDeclaredMethod("currentProcessName")

            declaredMethod.setAccessible(true)
            val invoke: Any = declaredMethod.invoke(null)
            if (invoke is String) {
                return invoke
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    fun getCurrentProcessNameByActivityManager(context: Context): String? {
        val pid: Int = Process.myPid()
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (am != null) {
            val runningAppList =
                am.runningAppProcesses
            if (runningAppList != null) {
                for (processInfo in runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName
                    }
                }
            }
        }
        return null
    }
}
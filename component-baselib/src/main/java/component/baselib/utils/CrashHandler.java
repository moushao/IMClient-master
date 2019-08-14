package component.baselib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import component.baselib.common.AppManager;
import component.data.common.Constants;

/**
 * @author Mou
 * @ClassName CrashHandler
 * @Description 未捕获异常处理类。在Application中获取实例并初始化：CrashHandler.getInstance().init();
 * @date 2016-6-13 下午2:29:43
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultCashHandler;
    private Context context;
    private String FILE_NAME = "crash";
    private String FILE_NAME_SUFFIX = ".txt";

    private CrashHandler() {
    }

    private static class InstanceHolder {
        private static CrashHandler crashHandler = new CrashHandler();
    }

    /**
     * @return CrashHandler
     * @Description 获取实例
     * @CreateDate 2016-6-13下午2:44:08
     */
    public static CrashHandler getInstance() {
        return InstanceHolder.crashHandler;
    }

    /**
     * @param context
     * @Description 初始化未捕获异常处理
     * @CreateDate 2016-6-13下午2:48:35
     */
    public void init(Context context) {
        defaultCashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 导出异常
            dumpExceptionToSDCard(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //系统重启
        //        restartApplication();
        // 结束程序
        AppManager.getAppManager().finishAllActivity();
        if (defaultCashHandler != null) {
            defaultCashHandler.uncaughtException(thread, ex);
        }
        Process.killProcess(Process.myPid());
    }

    /**
     * @param ex
     * @Description 保存异常到文件
     * @Author Mou
     * @CreateDate 2016-6-13下午2:52:58
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    private void dumpExceptionToSDCard(Throwable ex) {
        // 没有SDCard
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        // 创建路径
        PackageManager packManager = context.getPackageManager();
        String path = Constants.PHONE_PATH + "crashLog" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 写文件
        File file = new File(path + DateUtils.getCurrentDateString() + FILE_NAME_SUFFIX);

        PrintWriter pw = null;
        try {
            //			file.createNewFile();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)), true);
            //版本信息
            PackageInfo packageInfo = packManager.getPackageInfo(context.getPackageName(), PackageManager
                    .GET_ACTIVITIES);
            pw.print("App Version: ");
            pw.print(packageInfo.versionName);
            pw.print("_");
            pw.println(packageInfo.versionCode);
            //Android 版本
            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);
            //手机信息
            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);
            pw.print("Model: ");
            pw.println(Build.MODEL);
            pw.print("CPU ABI: ");
            pw.println(Build.CPU_ABI);
            //异常信息
            ex.printStackTrace(pw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }


    /**
     * @功能描述：传说中的重启自身
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void restartApplication() {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

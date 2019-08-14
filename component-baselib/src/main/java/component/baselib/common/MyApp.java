package component.baselib.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.luck.picture.lib.PictureSelectorActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;

import java.util.Iterator;
import java.util.List;

import component.baselib.arouter.RouterCenter;
import component.baselib.utils.CrashHandler;
import component.baselib.utils.DateUtils;
import component.baselib.utils.ScreenUtil;
import component.baselib.utils.SharedPreferencesHelper;
import component.data.common.Constants;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.unit.Subunits;

/**
 * 类名: MyApp
 * <br/> 功能描述:
 * <br/> 作者: MouTao
 * <br/> 时间: 2017/6/21
 */

public class MyApp extends Application {
    private static MyApp application;

    public static MyApp getApplication() {
        return application;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAutoSize();
        application = this;
        initLeakCanary();
        CrashHandler.getInstance().init(application);
        //ARouter路由初始化
        RouterCenter.init(this);
        initSmartRefresh();
        initScreenSize();
        initDB();
        addActivityLifeCycle();
    }


    //初始化内存泄漏检测工具
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    //在application中完成DaoSession的初始化，避免以后重复初始化，便于使用
    private void initDB() {
        //GreenDaoManager.getInstance().setDatabase(application);
    }

    //增加activity的生命注册方法，用于判断长时间从后台停留后，数据被回收
    private void addActivityLifeCycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initScreenSize() {
        Constants.SCREEN_WIDTH = ScreenUtil.getScreenWidth(application);
        Constants.SCREEN_HEIGHT = ScreenUtil.getScreenHeight(application);
    }

    public boolean isFirstLoad() {
        //判断是否为第一次登录,如果是,则进入资料引导页,否则直接进入主界面
        boolean isFirst = SharedPreferencesHelper.getBoolean(this, "FIRST", true);
        SharedPreferencesHelper.putBoolean(getApplicationContext(), "FIRST", false);
        return isFirst;
    }

    private void initSmartRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processName;
    }


    /**
     * 获取应用的版本号
     *
     * @return 应用版本号，默认返回1
     */
    public static int getAppVersionCode() {
        Context context = application.getApplicationContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
     * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
     */
    public void initAutoSize() {
        //Density.setDensity(this, 360);
        AutoSizeConfig.getInstance()
                .getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
        //.setLog(false)
        //.setUseDeviceSize(true)
        //.setBaseOnWidth(false)
        //.setAutoAdaptStrategy(new AutoAdaptStrategy())
        ;
        AutoSizeConfig.getInstance().getExternalAdaptManager()
                .addExternalAdaptInfoOfActivity(PictureSelectorActivity.class, new ExternalAdaptInfo(true, 400));
    }

    /**
     * 设置token
     *
     * @param token
     */
    public void setToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenValue", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DateUtils.getCurrentDateWithOutTimeString(), token);
        editor.commit();
    }

    /**
     * 获取当天的token值   如果查出来为空就重新请求一次token
     *
     * @return
     */
    public String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenValue", MODE_PRIVATE);
        return sharedPreferences.getString(DateUtils.getCurrentDateWithOutTimeString(), "");
    }

}



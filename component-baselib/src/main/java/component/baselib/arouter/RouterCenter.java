package component.baselib.arouter;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import component.baselib.BuildConfig;
import component.data.common.Constants;

/**
 * @Created by TOME .
 * @时间 2018/4/26 10:19
 * @描述 ${路由中心}
 */
//ARouter 提供了大量的参数类型 跳转携带 https://blog.csdn.net/zhaoyanjun6/article/details/76165252
public class RouterCenter {

    public static void init(Application app) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug();    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app);      // 尽可能早，推荐在Application中初始化
    }
}

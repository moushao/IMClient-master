package component.baselib.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import component.baselib.common.MyApp;

import static java.util.regex.Pattern.matches;


/**
 * Created by MouShao on 2018/5/31.
 */

public class PhoneHelper {
    private final MyApp cusApp;
    /**
     * TelephonyManager对象的引用
     */
    private TelephonyManager tm;

    /**
     * 采用静态内部类方式初始化单例对象
     *
     * @author Justice
     */
    private static class SingletonHolder {
        // 在这里定义静态引用常量instance指向单例类的实例
        // 只有在显式的调用静态内部类时才会实例化，并不会随类的加载而实例化对象
        private static final PhoneHelper instance = new PhoneHelper();
    }

    /**
     * 工厂方法，返回一个本类的实例
     *
     * @return 返回PhoneHelper类的实例
     */
    public static PhoneHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 构造方法中对TelephonyManager对象进行初始化，得到一个可以获得手机信息的对象
     */
    private PhoneHelper() {
        cusApp = MyApp.getApplication();
        tm = (TelephonyManager) cusApp.getSystemService(Context.TELEPHONY_SERVICE);

    }

    /**
     * 得到手机SIM卡的窜号
     *
     * @return 返回手机SIM卡的窜号
     */
    public String getICCID() {
        if (ActivityCompat.checkSelfPermission(cusApp, Manifest.permission.READ_PHONE_STATE) != PackageManager
                .PERMISSION_GRANTED) {
            return "";
        }
        return tm.getSimSerialNumber();
    }

    /**
     * 得到唯一的手机SIM卡的IMSI码
     *
     * @return 返回唯一的手机SIM卡的IMSI码
     */
    public String getIMSI() {
        if (ActivityCompat.checkSelfPermission(cusApp, Manifest.permission.READ_PHONE_STATE) != PackageManager
                .PERMISSION_GRANTED) {
            return "";
        }
        return tm.getSubscriberId();
    }

    /**
     * 得到唯一的手机设备号
     *
     * @return 返回唯一的手机设备号
     */
    public String getIMEI() {
        if (ActivityCompat.checkSelfPermission(cusApp, Manifest.permission.READ_PHONE_STATE) != PackageManager
                .PERMISSION_GRANTED) {
            return "";
        }
        return tm.getDeviceId() != null ? tm.getDeviceId() : "";
    }

    /**
     * @return 获取设备型号
     */
    public String getModel() {
        return Build.MODEL;
    }

    public boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147)|(145)|(149))\\d{8}$";
//        String regExp = "^(0\\\\d{2,3}-\\\\d{7,8}(-\\\\d{3,5}){0,1})|(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8})$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /*** 验证电话号码
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public boolean isTelephone(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
        Log.e("TAG", "phoneNum4:" + matches(regex, str));
        return matches(regex, str);
    }


}

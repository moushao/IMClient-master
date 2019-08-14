package component.baselib.utils;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by MouShao on 2017/9/8.
 */

public class RetrofitErrorUtlis {
    public static String getNetErrorMessage(Throwable t) {
        LogUtils.e("RetrofitError", t.toString());
        if (t instanceof SocketTimeoutException) {
            return "服务器读取超时,请重试";
        } else if (t instanceof ConnectException) {
            return "链接失败,请重试";
        } else if (t instanceof RuntimeException) {
            return "请求失败,请重试";
        } else if (t instanceof JsonSyntaxException) {
            return "数据解析失败";
        }
        return t.getMessage();
    }
}

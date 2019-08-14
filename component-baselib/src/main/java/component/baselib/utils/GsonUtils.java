package component.baselib.utils;

import com.google.gson.GsonBuilder;

/**
 * 类名: {@link GsonUtils}
 * <br/> 功能描述:Gson工具类
 * <br/> 作者: MouShao
 * <br/> 时间: 2018/3/5
 */
public class GsonUtils {
    /**
     * <br/> 方法名称: toJsonWithNulls
     * <br/> 方法详述: 将实体类序列化成
     * <br/> 参数: Object
     * <br/> 返回值:String
     */
    public static String toJsonWithNulls(Object bean) {
        String jsonString = new GsonBuilder().serializeNulls().create().toJson(bean);
        return jsonString;
    }
}

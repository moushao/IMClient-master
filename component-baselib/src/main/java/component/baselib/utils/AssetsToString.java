/**
 * @文件名：AssestToString.java
 * @author Administrator user=chenpan
 * @创建日期 2015-10-22
 * @功能描述：
 */
package component.baselib.utils;


import java.io.IOException;
import java.io.InputStream;

import component.baselib.common.MyApp;

/**
 * 类名: {@link AssetsToString}
 * <br/> 功能描述: 读取Assest文件内容转为字符串
 * <br/> 作者: MouTao
 * <br/> 时间: 2017/5/17
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class AssetsToString {

    public static String xmlToString(String stringName) {
        InputStream xml = null;
        String result = null;
        try {
            xml = MyApp.getApplication().getResources().getAssets().open(stringName);

            int length = xml.available();// 获取文字字数
            byte[] buffer = new byte[length];
            xml.read(buffer);// 读到数组中
            // 设置编码
            result = new String(buffer, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}

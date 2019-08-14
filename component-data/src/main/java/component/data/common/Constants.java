package component.data.common;


import android.os.Environment;


import java.io.File;

/**
 * 类名: {@link Constants}
 * <br/> 功能描述: 存储全局数据的基本类
 * <br/> 作者: MouTao
 * <br/> 备注: 请不用吐槽我使用接口来定义常量，千金难买我高兴
 */
public class Constants {
    /**
     * App名称
     */
    public static final String APP_NAME = "IM";
    /**
     * 从哪儿来
     */
    public final static String FROM = "FROM";

    /**
     * 手机存储路径
     * Environment.getExternalStorageDirectory();
     */
    public final static String PHONE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "CHPolice" + File.separator;


    public static String FILE_PATH_AUDIO = Constants.PHONE_PATH + "/audio/"; // 录音存储路径
    public static String SDVideoSavePath = Constants.PHONE_PATH + "/video/"; // 视频存储路径


    public static final boolean IS_PRINT = true;

    /**
     * 基础连接
     */

    public static String BASE_URL_DEBUG = "http://117.139.13.82:9009/";//公司外网debug
    public static String BASE_URL_RELEASE = "http://117.139.13.82:9009/";//公司外网release
    //文件Base链接
    public static String CHAT_FILE_BASE_URL = "http://117.139.13.82:9090/";

    public static String IM_HOST = "192.168.21.26";//测试IMip

    public static int IM_PORT = 9090;//测试IM端口

    /**
     * 屏幕宽度
     */
    public static int SCREEN_WIDTH;
    /**
     * 屏幕高度
     */
    public static int SCREEN_HEIGHT;

}

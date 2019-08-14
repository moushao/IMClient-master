package component.baselib.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import component.baselib.R;
import component.baselib.common.MyApp;
import component.data.common.Constants;

/**
 * 类名: {@link TipsManager}
 * <br/> 功能描述:消息发送综合管理器
 * <br/> 作者: MouShao
 * <br/> 时间: 2018/8/16
 */

public class TipsManager {
    private MediaPlayer player;

    public static TipsManager getInstance() {
        return SingletonHolder.instance;
    }


    private static class SingletonHolder {
        public static TipsManager instance = new TipsManager();
    }

    public void ringForMessage() {
        vibrate(200);
        playerDefaultVoice();
    }

    /**
     * <br/> 方法名称: vibrate
     * <br/> 方法详述:消息提醒震动
     * <br/> 参数: 震动时间长
     */
    public void vibrate(long milliseconds) {
        try {
            Vibrator vibrator = (Vibrator) MyApp.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(milliseconds);
        } catch (Exception e) {

        }
    }

    /**
     * <br/> 方法名称: playerDefaultVoice
     * <br/> 方法详述:播放系统默认提示音
     */
    public void playerDefaultVoice() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(MyApp.getApplication(), notification);
            if (!r.isPlaying())
                r.play();
        } catch (Exception e) {

        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
    }
}

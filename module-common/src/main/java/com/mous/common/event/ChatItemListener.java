package com.mous.common.event;

import android.view.View;

import com.mous.common.bean.ChatInfo;

import component.baselib.event.ItemListener;

/**
 * Created by MouShao on 2018/8/22.
 */

public interface ChatItemListener<T> extends ItemListener {
    /**
     * 判断是否需要显示时间
     */
    boolean isShowChatTime(int position, ChatInfo data);

    /**
     * 隐藏输入中心控制平台扩展菜单
     */
    void hideExtraLayout(View view, int position, ChatInfo data);

    /**
     * 预览图片
     */
    void previewPhoto(View view, int position, ChatInfo data);

    /**
     * 预览视频
     */
    void previewVideo(View view, int position, ChatInfo data);
}

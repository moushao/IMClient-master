package com.mous.common.event;


import com.mous.common.message.LastChatMessage;

import component.baselib.event.ItemListener;

/**
 * Created by MouShao on 2018/8/7.
 */

public interface SessionItemListener extends ItemListener {
    /**
     * <br/> 方法名称: deleteSession
     * <br/> 方法详述: 删除会话
     * <br/> 参数:
     * <br/> 返回值:
     * <br/> 异常抛出 Exception:
     * <br/> 异常抛出 NullPointerException:
     */
    void deleteSession(int position, LastChatMessage messageItem);

    /**
     * <br/> 方法名称: dragBadgeView
     * <br/> 方法详述: 拖拽未读消息泡泡
     * <br/> 参数:
     * <br/> 返回值:
     * <br/> 异常抛出 Exception:
     * <br/> 异常抛出 NullPointerException:
     */
    void dragBadgeView(int position, LastChatMessage messageItem);
}

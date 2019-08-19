package com.mous.common.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mous.common.R;
import com.mous.common.bean.ChatInfo;
import com.mous.common.event.ChatItemListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import component.baselib.adapter.VBaseHolder;
import component.baselib.utils.DateUtils;

import static android.view.View.VISIBLE;

/**
 * Created by MouShao on 2018/8/21.
 */

public class ChatHolder extends VBaseHolder<ChatInfo> {
    //聊天时间
    public TextView mChatTime;
    //泡泡布局
    public RelativeLayout mChatBubble;
    //对象昵称
    public TextView mChatName;
    //头像
    public ImageView mChatHead;
    //当前消息状态
    public TextView mChatStatus;
    //进度条
    public ProgressBar mChatBar;

    public ChatHolder(View itemView) {
        super(itemView);
        mChatBubble = itemView.findViewById(R.id.chat_row_bubble);
        mChatTime = itemView.findViewById(R.id.chat_row_time);
        mChatName = itemView.findViewById(R.id.chat_row_name);
        mChatHead = itemView.findViewById(R.id.chat_row_head);
        mChatStatus = itemView.findViewById(R.id.chat_row_status);
        mChatBar = itemView.findViewById(R.id.chat_row_progress_bar);

    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
        boolean isShowTime = ((ChatItemListener) mListener).isShowChatTime(position, mData);
        if (isShowTime) {
            mChatTime.setVisibility(VISIBLE);
            mChatTime.setText(DateUtils.DateFormatTime(mData.time.replace("/", "-")));
        } else {
            mChatTime.setVisibility(View.GONE);
        }


        //群聊接收到消息的时候,显示发送人姓名
        if (mData.isGroup && !mData.isMySend) {
            mChatName.setText(mData.senderName);
            mChatName.setVisibility(VISIBLE);
        }
    }

    @Override
    public void init() {
        super.init();
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ChatItemListener) mListener).hideExtraLayout(mView, position, mData);
                return false;
            }
        });
    }

    public void reSetImageViewSize(View view, int width, int height) {
        if (width <= 140 || height <= 140)
            return;
        if (width > height) {
            if (width > 140) {
                height = height * 140 / width;
                width = 140;
            }
        } else {
            if (height > 140) {
                width = width * 140 / height;
                height = 140;
            }
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();

        params.width = DensityUtil.dp2px(width);
        params.height = DensityUtil.dp2px(height);
        view.setLayoutParams(params);
    }
}

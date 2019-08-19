package com.mous.common.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mous.common.R2;
import com.mous.common.bean.ChatInfo;
import com.mous.common.event.ChatItemListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MouShao on 2018/8/22.
 */

class VedioChatHolder extends ChatHolder {

    @BindView(R2.id.chat_row_vedio) ImageView mChatRowVedio;
    @BindView(R2.id.chat_row_vedio_time) TextView mChatRowVedioTime;

    public VedioChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
    }

    @OnClick(R2.id.chat_row_vedio)
    public void onViewClicked() {
        ((ChatItemListener) mListener).previewVideo(mView, position, mData);
    }
}

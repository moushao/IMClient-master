package com.mous.common.adapter;

import android.view.View;
import android.widget.TextView;

import com.mous.common.R;
import com.mous.common.bean.ChatInfo;

/**
 * Created by MouShao on 2018/8/15.
 */

public class TextChatHolder extends ChatHolder {
    private TextView mContent;

    public TextChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void init() {
        super.init();
        mContent = mView.findViewById(R.id.chat_row_content);
    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
        mContent.setText(mData.content);
    }
}

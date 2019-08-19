package com.mous.common.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mous.common.R2;
import com.mous.common.bean.ChatInfo;

import butterknife.BindView;
import butterknife.OnClick;
import component.baselib.utils.FileOpenUtils;

/**
 * Created by MouShao on 2018/8/15.
 */

public class FileChatHolder extends ChatHolder {
    @BindView(R2.id.chat_row_file_name) TextView fileName;
    @BindView(R2.id.chat_row_file_size) TextView fileSize;
    @BindView(R2.id.chat_row_file_accepte) TextView fileAccepte;
    @BindView(R2.id.chat_row_file_refuse) TextView fileRefuse;
    @BindView(R2.id.chat_row_bubble) RelativeLayout bubbleLayout;

    public FileChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
        fileName.setText(mData.fileName);
    }

    @OnClick(R2.id.chat_row_bubble)
    public void onViewClicked() {
        FileOpenUtils.openFileByPath(mContext, mData.fileLocalPath);
    }
}

package com.mous.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mous.common.R;
import com.mous.common.R2;
import com.qmuiteam.qmui.widget.QMUILoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MouShao on 2019/8/5.
 */

public class ConnectStatusWidget extends LinearLayout {
    @BindView(R2.id.connect_widget_loading) QMUILoadingView mConnectLoading;
    @BindView(R2.id.connect_widget_tv) TextView mConnectTv;
    @BindView(R2.id.connect_widget_image) ImageView mConnectImg;
    private Context mContext;

    public ConnectStatusWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_connect_widget, this);
        ButterKnife.bind(view);
    }

    public void changeConnectStatus(int status) {
        switch (status) {
            case 0:
                setVisibility(VISIBLE);
                mConnectTv.setText("正在连接聊天服务器...");
                mConnectTv.setVisibility(VISIBLE);
                mConnectLoading.setVisibility(VISIBLE);
                mConnectImg.setVisibility(GONE);
                break;
            case 1:
                setVisibility(GONE);
                break;
            case -1:
                setVisibility(VISIBLE);
                mConnectTv.setText("网络连接不成功,请检查是否有可用网络!");
                mConnectLoading.setVisibility(GONE);
                mConnectTv.setVisibility(VISIBLE);
                mConnectImg.setVisibility(VISIBLE);
                break;
        }
    }
}

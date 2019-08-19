package com.mous.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by MouShao on 2018/8/10.
 */

public abstract class InputBaseWidget extends LinearLayout {
    public Context mContext;

    public InputBaseWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWidget();
    }


    //初始化控件
    protected abstract void initWidget();


}

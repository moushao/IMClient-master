package com.mous.common.widget.speech;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mous.common.R;


class DialogManager {

    /**
     * 以下为dialog的初始化控件，包括其中的布局文件
     */

    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private Context mContext;

    DialogManager(Context context) {
        mContext = context;
    }

    void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_audioDialog);
        View view = View.inflate(mContext, R.layout.dialog_manager, null);
        mDialog.setContentView(view);


        mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.recorder_dialogtext);
        mDialog.show();

    }

    /**
     * 设置正在录音时的dialog界面
     */
    void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLabel.setText("手指上滑，取消发送");
        }
    }

    /**
     * 取消界面
     */
    void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLabel.setText("松开手指，取消发送");
        }

    }

    // 时间过短
    void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLabel.setText("松开手指，取消发送");
        }
    }

    // 隐藏dialog
    void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }

    }

    void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {

            //先不改变它的默认状态
            //			mIcon.setVisibility(View.VISIBLE);
            //			mVoice.setVisibility(View.VISIBLE);
            //			mLable.setVisibility(View.VISIBLE);

            //通过level来找到图片的id，也可以用switch来寻址，但是代码可能会比较长
            int resId = mContext.getResources().getIdentifier("v" + level,
                    "drawable", mContext.getPackageName());

            mVoice.setImageResource(resId);
        }

    }

}

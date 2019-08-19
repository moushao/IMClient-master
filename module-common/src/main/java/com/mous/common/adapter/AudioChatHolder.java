package com.mous.common.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.mous.common.R2;
import com.mous.common.bean.ChatInfo;

import butterknife.BindView;
import butterknife.OnClick;
import component.baselib.utils.MediaManager;

/**
 * Created by MouShao on 2018/8/22.
 */

class AudioChatHolder extends ChatHolder {
    AnimationDrawable animationDrawable;
    @BindView(R2.id.chat_row_audio) ImageView mChatRowAudio;
    //是否正在播放
    private boolean isPlaying;

    public AudioChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
        setAudioViewSize();
        //mChatStatus.setText((int) mData.getMediaTime() + "''");
        mChatStatus.setVisibility(View.VISIBLE);
    }

    private void setAudioViewSize() {
        /*ViewGroup.LayoutParams params = mChatBubble.getLayoutParams();
        DecimalFormat df = new DecimalFormat("0.00");
        int width = (int) (120 * (1 + Float.valueOf(df.format(mData.getMediaTime() / 19))));
        params.width = width > 380 ? 380 : width;
        mChatBubble.setLayoutParams(params);*/
    }

    @OnClick(R2.id.chat_row_bubble)
    public void onViewClicked() {
        if (isPlaying) {
            stopAudio();
        } else {
            initAnimation();
        }
        isPlaying = !isPlaying;

    }

    private void initAnimation() {
     /*   if (mData == null || mData.getMediaTime() <= 0 || TextUtils.isEmpty(mData.getMediaPath()))
            return;
        File file = new File(mData.getMediaPath());
        if (!file.exists()) {
            ToastUtils.showToast(mContext, "音频文件未找到");
            return;
        }
        Drawable background = mChatRowAudio.getBackground();
        mChatRowAudio.setTag(background);
        if (mData.getFromOrTo() == 0) {
            mChatRowAudio.setBackgroundResource(R.drawable.shape_audio_animation_receive);
        } else {
            mChatRowAudio.setBackgroundResource(R.drawable.shape_audio_animation_send);
        }

        final AnimationDrawable drawable = (AnimationDrawable) mChatRowAudio.getBackground();
        drawable.start();
        // 播放音频
        MediaManager.playSound(file.getAbsolutePath(),
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mChatRowAudio != null) {
                            isPlaying = false;
                            Drawable background = (Drawable) mChatRowAudio.getTag();
                            if (background != null) {
                                mChatRowAudio.setBackground(background);
                                drawable.stop();
                            }
                        }
                    }
                });*/
    }

    public void stopAudio() {
        MediaManager.release();
        if (mChatRowAudio != null) {
            Drawable background = (Drawable) mChatRowAudio.getTag();
            if (background != null) {
                mChatRowAudio.setBackground(background);
            }
        }
    }
}

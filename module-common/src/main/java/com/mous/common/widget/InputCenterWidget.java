package com.mous.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.ui.ChatActivity;
import com.mous.common.widget.speech.AudioRecordButton;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import component.baselib.utils.MediaManager;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by MouShao on 2018/8/10.
 */

public class InputCenterWidget extends InputBaseWidget {

    @BindView(R2.id.btn_change_voice) Button mVoice;
    @BindView(R2.id.btn_change_keyboard) Button mKeyboard;
    @BindView(R2.id.btn_speak) AudioRecordButton mBtnSpeak;
    @BindView(R2.id.et_message) EditText mMessage;
    @BindView(R2.id.iv_change_emoji) ImageView mEmoji;
    @BindView(R2.id.btn_change_menu) Button mMenu;
    @BindView(R2.id.btn_send) Button mBtnSend;
    @BindView(R2.id.input_widget_menu) MenuWidget mMenuWidget;


    private InputCenterListener mInputCenterListener;
    private int status = -1;
    TranslateAnimation showAnim;

    public InputCenterWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void initWidget() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_input_center, this);
        ButterKnife.bind(view);
        initSpeakWidget();
        setEditOnclickListener();
        initAnimation();
    }


    @OnClick({R2.id.btn_change_voice, R2.id.btn_change_keyboard, R2.id.iv_change_emoji, R2.id.btn_change_menu, R2.id
            .btn_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i != R.id.btn_change_voice || i != R.id.btn_send)
            mInputCenterListener.widowSizeChanged();
        if (i == R.id.btn_change_voice) {
            showKeyBoardAndVoiceButton(true);
        } else if (i == R.id.btn_change_keyboard) {
            showKeyBoardAndVoiceButton(false);
            QMUIKeyboardHelper.showKeyboard(mMessage, true);
        } else if (i == R.id.iv_change_emoji) {
            status = 1;
        } else if (i == R.id.btn_change_menu) {
            status = 2;
        } else if (i == R.id.btn_send) {
            mBtnSend.setVisibility(GONE);
            mMenu.setVisibility(VISIBLE);
            String message = mMessage.getText().toString().trim();
            if (TextUtils.isEmpty(message))
                return;
            mMessage.setText("");
            mInputCenterListener.sendTextMessage(message);
        }

    }


    private void setEditOnclickListener() {
        QMUIKeyboardHelper.setVisibilityEventListener((ChatActivity) mContext, new QMUIKeyboardHelper
                .KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    mInputCenterListener.widowSizeChanged();
                    mMessage.setCursorVisible(true);
                    mMenuWidget.setVisibility(GONE);
                } else {
                    mMessage.setCursorVisible(false);
                }
                status = -1;
            }
        });

        mMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        mMenuWidget.setVisibility(GONE);
                }
                return false;
            }
        });

        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnSend.setVisibility(TextUtils.isEmpty(s) ? GONE : VISIBLE);
                mMenu.setVisibility(TextUtils.isEmpty(s) ? VISIBLE : GONE);

            }
        });
    }


    private void initSpeakWidget() {
        mBtnSpeak.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                mInputCenterListener.sendAudioMessage(seconds, filePath);
            }
        });
    }

    //设置出场动画
    private void initAnimation() {
        showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);
    }

    //插入表情图片
    private void insertEmoji(CharSequence spannableBuilder) {
        int iCursor = Selection.getSelectionEnd((mMessage.getText()));
        mMessage.getText().insert(iCursor, spannableBuilder);
    }

    //删除内容或者表情
    private void deleteTextOrEmoji() {
        if (mMessage.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(mMessage.getText());
            int iCursorStart = Selection.getSelectionStart(mMessage.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "#[face/png/f_static_000.png]#";
                        (mMessage.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        (mMessage.getText()).delete(iCursorEnd - 1, iCursorEnd);
                    }
                } else {
                    (mMessage.getText()).delete(iCursorStart, iCursorEnd);
                }
            }
        }
    }

    //判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
    private boolean isDeletePng(int iCursorEnd) {
        String st = "#[face/png/f_static_000.png]#";
        String content = mMessage.getText().toString().substring(0, iCursorEnd);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),
                    content.length());
            String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    private void showAndHideWidget(InputBaseWidget showWidget, InputBaseWidget hideWidget) {
        hideWidget.setVisibility(GONE);
        showWidget.startAnimation(showAnim);
        showWidget.setVisibility(View.VISIBLE);
    }

    private boolean checkAndHideKeyBoard(InputBaseWidget widget) {
        if (widget.getVisibility() == VISIBLE)
            return true;
        QMUIKeyboardHelper.hideKeyboard(mMessage);
        showKeyBoardAndVoiceButton(false);
        return false;
    }


    private void showKeyBoardAndVoiceButton(boolean showVoiceIcon) {
        //mEmojiWidget.setVisibility(GONE);
        mMenuWidget.setVisibility(GONE);
        if (showVoiceIcon) {
            QMUIKeyboardHelper.hideKeyboard(mMessage);
            mVoice.setVisibility(GONE);
            mKeyboard.setVisibility(VISIBLE);
            mMessage.setVisibility(GONE);
            mBtnSpeak.setVisibility(VISIBLE);
        } else {
            mVoice.setVisibility(VISIBLE);
            mKeyboard.setVisibility(GONE);
            mMessage.setVisibility(VISIBLE);
            mBtnSpeak.setVisibility(GONE);
        }
    }

    public void setInputCenterListener(InputCenterListener inputCenterListener) {
        this.mInputCenterListener = inputCenterListener;
        mMenuWidget.setInputCenterListener(inputCenterListener);
    }

    public void setEmojiList(ArrayList<String> emojiList) {
    }

    public void hideExtraLayout() {
        QMUIKeyboardHelper.hideKeyboard(mMessage);
        mMenuWidget.setVisibility(GONE);
    }


    /**
     * 类名: InputCenterListener
     * <br/> 功能描述:发送消息的接口监听
     */
    public interface InputCenterListener {
        /**
         * <br/> 方法名称: 发送文字消息
         */
        void sendTextMessage(String message);

        /**
         * <br/> 方法名称: 拍照
         */
        void takePhoto();

        /**
         * <br/> 方法名称: 选择图片
         */
        void pickPicture();

        /**
         * <br/> 方法名称:选择视频
         */
        void picVideo();

        /**
         * <br/> 方法名称: 选择文件
         */
        void pickFile();

        /**
         * <br/> 方法名称:发送语音消息
         */
        void sendAudioMessage(float seconds, String audioPath);


        /**
         * <br/> 方法名称:界面尺寸变化
         */
        void widowSizeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        mBtnSpeak.release();
        MediaManager.release();
        super.onDetachedFromWindow();
    }

}

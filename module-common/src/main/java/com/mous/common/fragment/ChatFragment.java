package com.mous.common.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.adapter.ChatAdapter;
import com.mous.common.bean.ChatInfo;
import com.mous.common.event.ChatItemListener;
import com.mous.common.ui.ChatActivity;
import com.mous.common.widget.InputCenterWidget;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import component.baselib.base.BaseFragment;
import component.baselib.bean.MessageEvent;
import component.baselib.mvp.BasePresenter;
import component.baselib.utils.DateUtils;
import component.baselib.utils.MediaManager;
import component.baselib.utils.ToastUtils;
import component.data.common.Constants;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MouShao on 2018/8/10.
 */
public class ChatFragment extends BaseFragment {
    public static final String TAG = "CHAT_FRAGMENT";
    public static String TalkingID = "";
    public static boolean isGroupTalking;
    public static String TalkName = "";
    @BindView(R2.id.title_tv) TextView mTitleTv;
    @BindView(R2.id.more) TextView mMore;
    @BindView(R2.id.chat_fragment_refre) QMUIPullRefreshLayout mRefre;
    @BindView(R2.id.fragment_chat_reycler) RecyclerView mRecycler;
    @BindView(R2.id.fragment_chat_input) InputCenterWidget mInput;
    private int pageSize = 20;
    private boolean isLoadMore = false;
    private String TalkingType;
    private Context mContext;
    LinkedList<ChatInfo> list = new LinkedList<>();
    private ChatAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int location = 0;
    private int pageIndex = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            switch (msg.what) {
                case 0:
                    int pos = list.size() - 1;
                    mRecycler.smoothScrollToPosition(pos <= 0 ? 0 : pos);
                    break;
                case 1:
                    mRefre.finishRefresh();
                    mRecycler.scrollToPosition(0);
                    break;
            }
        }
    };

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initData() {
        isRegisterEvent = true;
        mContext = getActivity();
        TalkingID = getArguments().getString(ChatActivity.CONTACT_ID);
        isGroupTalking = getArguments().getBoolean(ChatActivity.CONTACT_TYPE, false);
        TalkName = getArguments().getString(ChatActivity.CONTACT_NAME);
        //TalkingType = isGroupTalking ? Constants.Group_Talking : Constants.Person_Talking;
        mTitleTv.setText(getArguments().getString(ChatActivity.CONTACT_NAME));
        mMore.setVisibility(View.VISIBLE);
        mMore.setText("更多");
    }

    @Override
    protected void initWidgetAndEvent() {
        setInputWidgetListener();
        initRecyclerView();
        initRefreshListener();
        //getHistoryChatInfo(false, 0, pageIndex);
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void onPause() {
        MediaManager.release();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        TalkingID = "";
        mRefre.setOnPullListener(null);
        super.onDestroy();
    }

    @OnClick({R2.id.title_back, R2.id.more})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.title_back) {
            getActivity().finish();
        } else if (i == R.id.more) {
            goToPreViewMoreInfo();
        }
    }

    private void setInputWidgetListener() {
        mInput.setInputCenterListener(new InputCenterWidget.InputCenterListener() {
            @Override
            public void sendTextMessage(String message) {
                if (ChatFragment.isGroupTalking) {
                    //addChatInfoAndDisplay(IMSendManager.getInstance().getTextChatInfo(message), true);
                } else {
                    //addChatInfoAndDisplay(IMSendManager.getInstance().getTextChatInfo(message), true);
                }
            }

            @Override
            public void takePhoto() {
                PictureSelector.create(ChatFragment.this).openCamera(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void pickPicture() {
                gotoSelectImageOrVideo(PictureMimeType.ofImage(), false, PictureConfig.TYPE_IMAGE);
            }

            @Override
            public void picVideo() {
                gotoSelectImageOrVideo(PictureMimeType.ofVideo(), true, PictureConfig.TYPE_VIDEO);
            }

            @Override
            public void pickFile() {
                gotoSelectDocument();
            }

            @Override
            public void sendAudioMessage(float seconds, String audioPath) {
                //addChatInfoAndDisplay(MessageSendManager.getInstance().sendAudioMessage(seconds, audioPath), true);
            }

            @Override
            public void widowSizeChanged() {
                scrollToRecycEnd();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(mContext)
                .setData(list)
                .setListener(new ChatItemListener() {
                    @Override
                    public boolean isShowChatTime(int position, ChatInfo data) {
                        if (position == 0)
                            if (TextUtils.isEmpty(DateUtils.DateFormatTime(list.get(0).time.replaceAll("/", "-"))))
                                return false;
                            else
                                return true;
                        else
                            return (DateUtils.str2Date(data.time.replaceAll("/", "-")).getTime() - DateUtils.str2Date(
                                    list.get(position - 1).time.replaceAll("/", "-")).getTime()) / 1000 >
                                    180 ? true : false;
                    }

                    @Override
                    public void hideExtraLayout(View view, int position, ChatInfo data) {
                        mInput.hideExtraLayout();
                    }

                    @Override
                    public void previewPhoto(View view, int position, ChatInfo data) {
                        gotoPreviewPicture(data);
                    }

                    @Override
                    public void previewVideo(View view, int position, ChatInfo data) {
                    }

                    @Override
                    public void onItemClick(View view, int position, Object mData) {

                    }
                });
        mRecycler.setAdapter(adapter);
        mRecycler.scrollToPosition(list.size() - 1);
        mRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mInput.hideExtraLayout();
                return false;
            }
        });
    }

    private void initRefreshListener() {
        mRefre.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                isLoadMore = true;
            }
        });
    }

    private void finishRefresh(final boolean isLoadMore, int delay) {
        if (mRefre == null)
            return;
        mRefre.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRefre == null)
                    return;
                mRefre.finishRefresh();
                adapter.notifyDataSetChanged();
                int pos = isLoadMore ? list.size() - location + 8 : list.size() - 1;
                mRecycler.scrollToPosition(pos);
            }
        }, delay);
    }


    private void goToPreViewMoreInfo() {
      /*  if (TalkingType.equals(Constants.Person_Talking)) {//单独聊天
            RouterCenter.toMailPersonDetail(TAG, TalkingID);
        } else {//群聊
            RouterCenter.toMailGroupDetail(TAG, TalkingID);
        }*/
    }


    private void addChatInfoAndDisplay(ChatInfo info, boolean isInsert) {
        list.add(info);
        scrollToRecycEnd();
    }

    //发送消息让Recycler显示最后一行
    private void scrollToRecycEnd() {
        mHandler.sendMessage(mHandler.obtainMessage(0));
    }

    public void gotoSelectDocument() {
        Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT);
        intentFile.setType("*/*");
        intentFile.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            /*startActivityForResult(Intent.createChooser(intentFile, "请选择一个要上传的文件"), FilePickerConst
                    .REQUEST_CODE_DOC);*/
        } catch (android.content.ActivityNotFoundException ex) {
            ToastUtils.showToast(mContext, "请安装文件管理器");
        }
    }

    private void gotoSelectImageOrVideo(int type, boolean isCamera, int requestCode) {
        PictureSelector.create(ChatFragment.this)
                .openGallery(type)
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture
                .maxSelectNum(1)// 最大图片选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .setOutputCameraPath(Constants.PHONE_PATH + "/image/photo")// 自定义拍照保存路径
                .compressSavePath(Constants.PHONE_PATH + "/image/compress")//压缩图片保存地址
                .compress(false)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .videoMaxSecond(10)
                .videoMinSecond(1)// 显示多少秒以内的视频or音频也可适用 int 
                .recordVideoSecond(10)//视频秒数录制 默认60s int
                .previewVideo(true)// 是否可预览视频 true or false
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .forResult(requestCode);//结果回调onActivityResult code

    }

    //我正在群聊这个界面   当我接收到被踢出群和解散群的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventRefreshEvent(MessageEvent item) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            /*case PictureConfig.CHOOSE_REQUEST://拍照
            case Configs.REQUEST_CODE_PHOTO://图片
                selectList.addAll(PictureSelector.obtainMultipleResult(data));
                String imagePath = PictureSelector.obtainMultipleResult(data).get(0).getPath();
                addChatInfoAndDisplay(IMSendManager.getInstance().getImageChatInfo(imagePath), true);
                break;
            case FilePickerConst.REQUEST_CODE_DOC://文件
                Uri fileUri = data.getData();
                String filePath = FileUtils.getPath(mContext, fileUri);
                //                String filePath = UriTofilePath.getFilePathByUri(mContext, data.getData());
                addChatInfoAndDisplay(IMSendManager.getInstance().getFileChatInfo(filePath), true);
                // fileMap.put(info.getFileChatInfo().projectId, list.size() - 1);
                break;
            case Configs.REQUEST_CODE_VEDIO://视频
                String videoUrl = PictureSelector.obtainMultipleResult(data).get(0).getPath();
                addChatInfoAndDisplay(IMSendManager.getInstance().getVideoChatInfo(videoUrl), true);
                break;
*/
        }
    }

    //初始化预览照片吗
    private void gotoPreviewPicture(ChatInfo data) {
        List<LocalMedia> medias = new ArrayList<>();
        int mediaPosition = 0;
        for (int i = 0; i < list.size(); i++) {
            ChatInfo info = list.get(i);
            if (info.infoType == 4) {
                if (TextUtils.isEmpty(info.filePath)) {
                    medias.add(new LocalMedia(info.fileLocalPath, 0, PictureConfig.TYPE_PICTURE, "image/jpeg"));
                } else {
                    medias.add(new LocalMedia(info.filePath, 0, PictureConfig.TYPE_PICTURE, "image/jpeg"));
                }
                if (data.filePath.endsWith(info.filePath)) {
                    mediaPosition = medias.size() - 1;
                }
            }
        }
        PictureSelector.create(ChatFragment.this)
                .themeStyle(R.style.picture_default_style)
                .openExternalPreview(mediaPosition, /*"/image/picture/",*/ medias);
    }
}

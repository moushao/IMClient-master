package com.mous.common.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.bean.ChatInfo;
import com.mous.common.event.ChatItemListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MouShao on 2018/8/15.
 */

public class ImageChatHolder extends ChatHolder {
    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.audio_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    @BindView(R2.id.chat_row_image) ImageView mChatImage;

    public ImageChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(int position, ChatInfo mData) {
        super.setData(position, mData);
        String imageUrl = TextUtils.isEmpty(mData.fileLocalPath) ? mData.filePath : mData.fileLocalPath;
        Glide.with(mChatImage.getContext())
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean
                            isFirstResource) {
                        //TODO 图片已经过期或者删除
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource
                            dataSource, boolean isFirstResource) {
                        reSetImageViewSize(mChatImage, bitmap.getWidth(), bitmap.getHeight());
                        return false;
                    }
                }).into(mChatImage);
    }


    @OnClick(R2.id.chat_row_image)
    public void onViewClicked() {
        ((ChatItemListener) mListener).previewPhoto(mView, position, mData);
    }
}

package com.mous.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.mous.common.R;
import com.mous.common.bean.ChatInfo;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import component.baselib.adapter.VBaseHolder;
import component.baselib.event.ItemListener;


public class ChatAdapter<T> extends DelegateAdapter.Adapter<VBaseHolder<T>> {
    //上下文
    public Context mContext;
    //布局文件资源ID
    public int mResLayout;
    //数据源
    public List<T> mDatas;
    //布局管理器
    private LayoutHelper mLayoutHelper;
    //继承VBaseHolder的内容Holder
    public Class<? extends VBaseHolder> mMutipleHolder;

    //回调监听
    public ItemListener mListener;

    public ChatAdapter(Context context) {
        mContext = context;
    }


    /**
     * <br/> 方法名称:ChatAdapter
     * <br/> 方法详述:构造函数
     * <br/> 参数:<同上申明>
     */
    public ChatAdapter(Context context, List<T> mDatas, int mResLayout, Class<? extends VBaseHolder> mClazz,
                       LayoutHelper layoutHelper, ItemListener listener) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mContext = context;
        this.mResLayout = mResLayout;
        this.mLayoutHelper = layoutHelper;
        this.mMutipleHolder = mClazz;
        this.mListener = listener;
        this.mDatas = mDatas;
    }

    /**
     * <br/> 方法名称: ChatAdapter
     * <br/> 方法详述: 设置数据源
     * <br/> 参数: mDatas，数据源
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
        return this;
    }

    public ChatAdapter addAllData(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
        return this;
    }

    /**
     * <br/> 方法名称: addItem
     * <br/> 方法详述: 设置单个数据源
     * <br/> 参数: mItem，单个数据源
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter addItem(T mItem) {
        this.mDatas.add(mItem);
        return this;
    }

    public ChatAdapter removeItem(int i) {
        if (mDatas.size() > i) {
            this.mDatas.remove(i);
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * <br/> 方法名称: setLayout
     * <br/> 方法详述: 设置布局资源ID
     * <br/> 参数: mutipleLayoutRes, 布局资源ID
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setLayout(int mResLayout) {
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mResLayout = mResLayout;
        return this;
    }

    /**
     * <br/> 方法名称: setLayoutHelper
     * <br/> 方法详述: 设置布局管理器
     * <br/> 参数: layoutHelper，管理器
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setLayoutHelper(LayoutHelper layoutHelper) {
        if (layoutHelper == null) {
            this.mLayoutHelper = new LinearLayoutHelper();
        }
        this.mLayoutHelper = layoutHelper;
        return this;
    }

    /**
     * <br/> 方法名称: setHolder
     * <br/> 方法详述: 设置holder
     * <br/> 参数: mutipleHolder,集成VBaseHolder的holder
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setHolder(Class<? extends VBaseHolder> mClazz) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        this.mMutipleHolder = mClazz;
        return this;
    }

    /**
     * <br/> 方法名称: setListener
     * <br/> 方法详述: 传入监听，方便回调
     * <br/> 参数: listener
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setListener(ItemListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * <br/> 方法名称: onCreateLayoutHelper
     * <br/> 方法详述: 继承DelegateAdapter.Adapter后重写方法，告知elegateAdapter.Adapter使用何种布局管理器
     * <br/> 参数:
     * <br/> 返回值:  ChatAdapter
     */
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper == null ? new LinearLayoutHelper() : mLayoutHelper;
    }


    public HashMap<Integer, Object> tags = new HashMap<>();

    /**
     * <br/> 方法名称: setTag
     * <br/> 方法详述: 设置mObject
     * <br/> 参数: mObject
     * <br/> 返回值:  ChatAdapter
     */
    public ChatAdapter setTag(int tag, Object mObject) {
        if (mObject != null) {
            tags.put(tag, mObject);
        }
        return this;
    }

    /**
     * <br/> 方法名称: onCreateViewHolder
     * <br/> 方法详述: 解析布局文件，返回传入holder的构造器
     */
    @Override
    public VBaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        int resLayout = 0;
        Class<? extends VBaseHolder> holder = null;
        switch (viewType) {
            case 0:         //文字收
                resLayout = R.layout.layout_chat_row_message_receive;
                holder = TextChatHolder.class;

                break;
            case 1:         //文字发
                resLayout = R.layout.layout_chat_row_message_send;
                holder = TextChatHolder.class;
                break;
            case 2:         //文件收
                resLayout = R.layout.layout_chat_row_file_receive;
                holder = FileChatHolder.class;
                break;
            case 3:         //文件发
                resLayout = R.layout.layout_chat_row_file_send;
                holder = FileChatHolder.class;
                break;
            case 4:         //语音收
                resLayout = R.layout.layout_chat_row_audio_reveive;
                holder = AudioChatHolder.class;
                break;
            case 5:         //语音发
                resLayout = R.layout.layout_chat_row_audio_send;
                holder = AudioChatHolder.class;
                break;
            case 6:         //视频收
                resLayout = R.layout.layout_chat_row_vedio_receive;
                holder = VedioChatHolder.class;
                break;
            case 7:         //视频发
                resLayout = R.layout.layout_chat_row_vedio_send;
                holder = VedioChatHolder.class;
                break;
            case 8:         //图收
                resLayout = R.layout.layout_chat_row_image_receive;
                holder = ImageChatHolder.class;
                break;
            case 9:         //图发
                resLayout = R.layout.layout_chat_row_image_send;
                holder = ImageChatHolder.class;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(resLayout, parent, false);
        if (tags != null && tags.size() > 0) {
            for (int tag : tags.keySet()) {
                view.setTag(tag, tags.get(tag));
            }
        }

        try {
            Constructor<? extends VBaseHolder> mClazzConstructor = holder.getConstructor(View.class);
            if (mClazzConstructor != null) {
                return mClazzConstructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <br/> 方法名称: onBindViewHolder
     * <br/> 方法详述: 绑定数据
     * <br/> 参数:
     * <br/> 返回值:  ChatAdapter
     */

    @Override
    public void onBindViewHolder(VBaseHolder holder, int position) {
        holder.setListener(mListener);
        holder.setContext(mContext);
        holder.setCount(mDatas.size());
        holder.setData(position, mDatas.get(position));
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        //此处返回count是为了方便控制显示个数
        return mDatas.size();
    }


    public void clear() {
        this.mDatas.clear();
    }

    @Override
    public int getItemViewType(int position) {
        ChatInfo chatInfo = (ChatInfo) mDatas.get(position);
        if (chatInfo.infoType == 6 || chatInfo.infoType == 5){
            chatInfo.infoType = 0;
        }
        return chatInfo.infoType * 2 + (chatInfo.isMySend ? 1 : 0);
    }
}

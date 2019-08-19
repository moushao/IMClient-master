package com.mous.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mous.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MouShao on 2018/8/10.
 */

public class MenuWidget extends InputBaseWidget {
    public int row = 2;//行数
    public int column = 4;//列数
    private List<GridBean> dataList;
    private List<GridView> gridList;
    private ViewPager mPager;


    private InputCenterWidget.InputCenterListener mInputCenterListener;

    public MenuWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGridView();
    }

    @Override
    protected void initWidget() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_widget_chat_menu, this);
        mPager = view.findViewById(R.id.widget_menu_viewpager);
    }

    private void initGridView() {
        dataList = new ArrayList<>();
        dataList.add(new GridBean("拍照", R.drawable.pic_chat_photo));
        dataList.add(new GridBean("照片", R.drawable.pic_chat_picture));
        dataList.add(new GridBean("视频", R.drawable.pic_chat_vedio));
        dataList.add(new GridBean("文件", R.drawable.pic_chat_file));
        //计算viewpager一共显示几页
        int pageSize = dataList.size() % (row * column) == 0
                ? dataList.size() / (row * column)
                : dataList.size() / (row * column) + 1;

        gridList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {

            GridView gridView = new GridView(mContext);
            GridViewAdapter adapter = new GridViewAdapter(dataList, i);
            gridView.setNumColumns(column);
            gridView.setAdapter(adapter);
            gridList.add(gridView);
        }
        ViewPagerAdapter mAdapter = new ViewPagerAdapter();
        mAdapter.add(gridList);
        mPager.setAdapter(mAdapter);
    }

    public class GridBean {
        public String itemName;
        public int itemRes;

        public GridBean(String itemName, int itemRes) {
            this.itemName = itemName;
            this.itemRes = itemRes;
        }
    }

    class GridViewAdapter extends BaseAdapter {
        private List<GridBean> dataList;
        private int page;

        GridViewAdapter(List<GridBean> datas, int page) {
            dataList = new ArrayList<>();
            this.page = page;
            //start end分别代表要显示的数组在总数据List中的开始和结束位置
            int start = page * (row * column);
            int end = start + (row * column);
            while ((start < datas.size()) && (start < end)) {
                dataList.add(datas.get(start));
                start++;
            }
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View itemView, ViewGroup viewGroup) {
            final ViewHolder mHolder;
            if (itemView == null) {
                mHolder = new ViewHolder();
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_widget_menu,
                        viewGroup,
                        false);
                mHolder.iv_img = (ImageView) itemView.findViewById(R.id.item_chat_widget_menu_image);
                mHolder.tv_text = (TextView) itemView.findViewById(R.id.item_chat_widget_menu_text);
                itemView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) itemView.getTag();
            }
            final GridBean bean = dataList.get(i);
            if (bean != null) {
                mHolder.iv_img.setImageResource(bean.itemRes);
                mHolder.tv_text.setText(bean.itemName);
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (bean.itemName) {
                            case "拍照":
                                mInputCenterListener.takePhoto();
                                break;
                            case "照片":
                                mInputCenterListener.pickPicture();
                                break;
                            case "视频":
                                mInputCenterListener.picVideo();
                                break;
                            case "文件":
                                mInputCenterListener.pickFile();
                                break;
                        }
                    }
                });
            }
            return itemView;
        }

        private class ViewHolder {
            private ImageView iv_img;
            private TextView tv_text;
        }
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<GridView> gridList;


        ViewPagerAdapter() {
            gridList = new ArrayList<>();
        }

        void add(List<GridView> datas) {
            if (gridList.size() > 0) {
                gridList.clear();
            }
            gridList.addAll(datas);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return gridList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(gridList.get(position));
            return gridList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void setInputCenterListener(InputCenterWidget.InputCenterListener inputCenterListener) {
        mInputCenterListener = inputCenterListener;
    }
}

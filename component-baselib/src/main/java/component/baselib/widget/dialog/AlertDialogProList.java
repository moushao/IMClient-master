package component.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import component.baselib.R;
import component.baselib.adapter.VBaseAdapter;
import component.baselib.adapter.VBaseHolder;
import component.baselib.utils.ScreenUtil;

/**
 * Created by MouShao on 2018/4/16.
 */

public class AlertDialogProList<T> {
    /**
     * 宽度比
     */
    private float widthRatio = 0.7f;
    private float heightRatio = 0.5f;
    private Display display;
    private Context context;
    private TextView mTitle;
    private RecyclerView mRecycler;
    private Dialog dialog;
    private List<T> mData;
    LinearLayout lLayout_bg;

    private VBaseAdapter mAdapter;
    public Class<? extends VBaseHolder> mHolder;
    //recyc的子项布局
    private int resLayout;

    public AlertDialogProList(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialogProList builder() {
        // 加载布局  dialog
        View view = LayoutInflater.from(context).inflate(R.layout.view_alertdialog_pro_list, null);

        // 寻找控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.pro_dialog);
        mTitle = (TextView) view.findViewById(R.id.pro_dialog_title);
        mRecycler = (RecyclerView) view.findViewById(R.id.pro_dialog_recyc);


        // 设置为dialog
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        initRecycler();
        return this;

    }

    private void setParams(boolean isWrap) {
        int width = (int) (display.getWidth() * widthRatio);
        int height = isWrap ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) (display.getHeight() * heightRatio);
        //        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // 设置大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {

        });

    }

    public AlertDialogProList setRecycLayout(int resLayout) {
        this.resLayout = resLayout;
        return this;
    }

    public AlertDialogProList setTitle(String title) {
        mTitle.setText(title);
        return this;
    }

    public AlertDialogProList setData(List<T> list) {
        this.mData = list;
        return this;
    }

    public AlertDialogProList setHolder(Class<? extends VBaseHolder> mClazz) {
        this.mHolder = mClazz;
        return this;
    }

    public AlertDialogProList setAdapter(VBaseAdapter adapter) {
        //        this.mAdapter = adapter;
        setParams(getIsWrap(adapter.getItemCount()));
        mRecycler.setAdapter(adapter);
        return this;
    }

    private boolean getIsWrap(int itemCount) {
        int height = (int) (ScreenUtil.getScreenHeight(context) * heightRatio);
        //if (height > itemCount * context.getResources().getInteger(R.integer.item_height))
        if (itemCount < 7)
            return true;
        else
            return false;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}

package component.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import component.baselib.R;


/**
 * Created by Mou on 2017/6/20.
 */

public class BottomDialog {
    private Context context;
    private Dialog dialog;
    private TextView item0, item1, item2;

    public BottomDialog(Context context) {
        this.context = context;
    }

    public BottomDialog builder() {
        // 加载布局  dialog
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buttom, null);
        //        // 寻找控件
        view.findViewById(R.id.bottom_dialog_empty_view).setOnClickListener(clickListener);
        view.findViewById(R.id.bottom_dialog_item_0).setOnClickListener(clickListener);
        item1 = view.findViewById(R.id.bottom_dialog_item_1);
        item2 = view.findViewById(R.id.bottom_dialog_item_2);
        // 设置为dialog
        dialog = new Dialog(context, R.style.BottomDialogStyle);

        dialog.setContentView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        return this;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.bottom_dialog_item_0 || v.getId() == R.id.bottom_dialog_empty_view) {
                dialog.dismiss();
            }
        }
    };

    public BottomDialog setItem1OnClick(String text, final View.OnClickListener listener) {
        if (!TextUtils.isEmpty(text))
            item1.setText(text);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;

    }

    public BottomDialog setItem2OnClick(String text, final View.OnClickListener listener) {
        if (!TextUtils.isEmpty(text))
            item2.setText(text);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public BottomDialog setDialogShowBottom() {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        dialogWindow.setAttributes(lp);
        return this;

    }

    public void show() {
        dialog.show();
    }
}

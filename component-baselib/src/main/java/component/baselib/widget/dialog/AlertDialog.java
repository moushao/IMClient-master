package component.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import component.baselib.R;


/**
 * 自定义仿IOS的对话框
 * 用法：
 * new AlertDialog(SettingActivity.this).builder().setTitle("清除缓存")
 * .setMsg("清除缓存后使用的流量可能会额外增加，确定清除？")
 * .setPositiveButton("确认", new OnClickListener() {
 *
 * @Override public void onClick(View v) {
 * FrecsoUtils.clear();
 * }
 * }).setNegativeButton("取消", new OnClickListener() {
 * @Override public void onClick(View v) {
 * <p>
 * }
 * }).show();
 */
public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_msg;
    private EditText txt_edit;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private RelativeLayout addViewLayout;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    private int messageGravity = Gravity.CENTER_VERTICAL;
    /**
     * 宽度比
     */
    private float widthRatio = 0.75f;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 加载布局  dialog
        View view = LayoutInflater.from(context).inflate(R.layout.view_alertdialog, null);

        // 寻找控件
        LinearLayout lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        addViewLayout = (RelativeLayout) view.findViewById(R.id.addViewLayout);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setGravity(messageGravity);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        txt_edit = (EditText) view.findViewById(R.id.txt_edit);
        img_line.setVisibility(View.GONE);

        // 设置为dialog
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                context = null;
            }
        });
        // 设置大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * widthRatio),
                LayoutParams.WRAP_CONTENT));
        return this;
    }


    /**
     * 添加自己的布局文件
     *
     * @param view
     * @return
     */
    public AlertDialog addView(View view) {
        // 设置大小
        view.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addViewLayout.addView(view);
        return this;

    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertDialog setMessageGravity(int message_Gravity) {
        txt_msg.setGravity(message_Gravity);
        return this;
    }

    public AlertDialog hideTitleLayout() {
        txt_title.setVisibility(View.GONE);
        return this;
    }

    public AlertDialog setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
        return this;
    }

    public AlertDialog setMsg(SpannableStringBuilder msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("标题");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("标题");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setTitleSize(int size) {
        txt_title.setTextSize(size);
        return this;
    }

    public AlertDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (txt_edit.getVisibility() == View.VISIBLE) {
                        String content = txt_edit.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            return;
                        } else {
                            v.setTag(content);
                        }
                    }
                    listener.onClick(v);
                }
                dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text, final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setEditText() {
        txt_edit.setVisibility(View.VISIBLE);
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("内容");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        } else {
            txt_title.setVisibility(View.GONE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_dialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_dialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void setDialogShowBottom() {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        dialogWindow.setAttributes(lp);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}

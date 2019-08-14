package component.baselib.widget.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import component.baselib.R;


public class ProgressDialog extends Dialog {


    static ProgressDialog instance;
    View view;
    TextView tvMessage;
    SpinKitView splshView;
    Context context;


    public static ProgressDialog getInstance(Context context) {
        if (instance != null && instance.isShowing()) {
            instance.dismissHUD();
        }
        instance = new ProgressDialog(context);
        return instance;
    }

    public ProgressDialog(Context context) {
        super(context, R.style.progress_dialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.context = context;
        view = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        tvMessage = (TextView) view.findViewById(R.id.loading_tv);
        splshView = (SpinKitView) view.findViewById(R.id.spin_kit);
        // 设置大小
        LinearLayout lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                .LayoutParams.WRAP_CONTENT));
        this.setContentView(view);
    }


    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    @Override
    public void show() {
        if (!((Activity) context).isFinishing()) {
            splshView = new SpinKitView(context);
            super.show();
        } else {
            instance = null;
        }
    }


    public void destroy() {
        context = null;
        instance = null;
        dismissHUD();
    }

    public void dismissHUD() {
        dismiss();
    }

}

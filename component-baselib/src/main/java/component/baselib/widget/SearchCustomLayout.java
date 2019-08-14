package component.baselib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIKeyboardHelper;

import component.baselib.R;

/**
 * Created by MouShao on 2018/4/12.
 */

public class SearchCustomLayout extends LinearLayout {
    //图标
    private EditText search_ed;
    //标题
    private ImageView search_delete;

    private Context mContext;

    public void setSearchListener(addSearchListener searchListener) {
        this.mSearchListener = searchListener;
    }

    private addSearchListener mSearchListener;

    public SearchCustomLayout(Context context) {
        super(context);
    }

    public SearchCustomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_search_custom, this);
        search_ed = findViewById(R.id.search_content);
        search_delete = findViewById(R.id.search_delete);
        initWidget();
    }

    private void initWidget() {
        search_ed.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            QMUIKeyboardHelper.hideKeyboard(search_ed);
                            if (mSearchListener != null) {
                                mSearchListener.searchContent(search_ed.getText().toString());
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        search_ed.setOnFocusChangeListener(new 
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    QMUIKeyboardHelper.hideKeyboard(search_ed);
                }
            }
        });
        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    search_delete.setVisibility(INVISIBLE);
                    if (mSearchListener != null){
                        mSearchListener.delete();
                    }
                } else {
                    search_delete.setVisibility(VISIBLE);
                }
            }
        });

        search_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                search_ed.setText("");
                if (mSearchListener != null)
                    mSearchListener.delete();
            }
        });

    }

    public interface addSearchListener {
        void searchContent(String searchContent);

        void delete();
    }


}

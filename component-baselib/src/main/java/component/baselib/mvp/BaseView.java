package component.baselib.mvp;

/**
 * MVP基础view
 */
public interface BaseView {

    void showLoading(String str);

    void disLoading();

    void showToast(String message);

    void onFailed(String message);

    
}
package component.baselib.mvp;


public abstract class BasePresenter<T extends BaseView> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public void requestFailed(String message) {
        if (mView != null) {
            mView.disLoading();
            mView.onFailed(message);
        }
    }
}
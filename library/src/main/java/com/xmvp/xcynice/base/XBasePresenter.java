package com.xmvp.xcynice.base;

import com.xmvp.xcynice.XBaseRetrofitConfig;
import com.xmvp.xcynice.http.API;
import com.xmvp.xcynice.http.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description : XBasePresenter
 *
 * @author XuCanyou666
 * @date 2020/2/7
 */


public class XBasePresenter<V extends XBaseView> {

    private CompositeDisposable compositeDisposable;
    public V baseView;



    protected RetrofitService retrofitService = RetrofitService.getInstance();

    public XBasePresenter(V baseView) {
        this.baseView = baseView;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 返回 view
     */
    @SuppressWarnings("all")
    public V getBaseView() {
        return baseView;
    }

    public void addDisposable(Observable<?> observable, XBaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable
                .add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
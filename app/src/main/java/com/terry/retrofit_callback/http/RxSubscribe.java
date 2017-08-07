package com.terry.retrofit_callback.http;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * *
 * name     RxSubscribe
 * Creater  Terry
 * time     2017/8/7
 * *
 **/

public abstract class RxSubscribe<T> implements Observer<BaseModel<T>> {

    public RxSubscribe() {
    }

    protected abstract void onSuccess(T t);

    protected void onFailed(int code, String msg) {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // 显示加载中对话框
    }

    @Override
    public void onComplete() {
        // 隐藏加载中对话框
    }

    @Override
    public void onNext(BaseModel<T> baseModel) {
        if (baseModel.code == 200) {
            onSuccess(baseModel.data);
        } else if (baseModel.code == 303) {
            //比如 做token无效统一处理
            onFailed(baseModel.code, baseModel.message);
        } else {
            onFailed(baseModel.code, baseModel.message);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ConnectException) {
            //网络连接失败
            onFailed(403, t.getMessage());
        } else {
            onFailed(405, t.getMessage());
        }
    }
}
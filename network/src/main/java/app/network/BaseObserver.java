package app.network;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import app.network.bean.BaseResp;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * File: BaseObserver
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/2 17:08
 * Description:
 */
public abstract class BaseObserver<T> implements Observer<BaseResp<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;
    private boolean errWithData = false;

    public BaseObserver(Context context) {
        mContext = context.getApplicationContext();
        errWithData = false;
    }

    public BaseObserver(Context context, boolean withData) {
        mContext = context.getApplicationContext();
        errWithData = withData;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Logger.d(TAG, "onSubscribe");
    }

    @Override
    public void onNext(BaseResp<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onSuccess(value.getMsg(), t);
        } else {
            if (errWithData) {
                onErrorWithData(value.getCode(), value.getMsg(), value.getData());
            } else {
                onReturnError(value.getCode(), value.getMsg());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e(TAG, "onError:" + e.toString());
        if (e instanceof HttpException) {
            HttpException httpException = ((HttpException) e);
            onReturnError(httpException.code(), httpException.message());
        } else {
            onReturnError(-1, e.getLocalizedMessage());
        }
    }

    /**
     * 错误处理
     *
     * @param code
     * @param msg
     */
    private void onReturnError(int code, String msg) {
        if (code == 403 || code == 401) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            Flowable.timer(3, TimeUnit.SECONDS)
//                    .observeOn(Schedulers.newThread())
//                    .subscribe(aLong -> {
//                        Loading.getInstance().dismiss();
//                        if (!AccountUtil.getInstance().isSessionExpired()) {
//                            AccountUtil.getInstance().logout();
//                        }
//                    });
        } else {
            onError(code, msg);
        }
    }

    @Override
    public void onComplete() {
        Logger.d(TAG, "onComplete");
    }

    public void onErrorWithData(int code, String msg, T data) {

    }

    protected abstract void onSuccess(String msg, T data);

    protected abstract void onError(int code, String msg);
}

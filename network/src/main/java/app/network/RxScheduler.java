package app.network;

import com.trello.rxlifecycle3.LifecycleTransformer;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * File: RxScheduler
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/2 17:22
 * Description:
 */
public class RxScheduler {
    public static <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    // 可添加网络连接判断等
//                                if (!Utils.isNetworkAvailable(BaseActivity.this)) {
//                                    Toast.makeText(BaseActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
//                                }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycle);
    }
}

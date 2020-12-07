package app.wxapkfiles.activity;

import android.widget.Toast;

import app.wxapkfiles.api.bean.req.HelloReq;
import app.wxapkfiles.api.bean.resp.HelloResp;
import app.wxapkfiles.base.BaseActivity;
import app.wxapkfiles.databinding.ActivityMainBinding;
import app.wxapkfiles.network.RetrofitFactory;
import app.network.BaseObserver;
import app.network.RxScheduler;
import app.network.bean.BaseResp;
import io.reactivex.Observable;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void initView() {
        binding.btnHttpTest.setOnClickListener(view -> httpTest());
    }

    private void httpTest() {
        HelloReq helloReq = new HelloReq();
        Observable<BaseResp<HelloResp>> observable = RetrofitFactory.getInstance().hello(helloReq.toRequestBody());
        observable.compose(RxScheduler.compose(this.bindToLifecycle())).subscribe(new BaseObserver<HelloResp>(this) {
            @Override
            protected void onSuccess(String msg, HelloResp data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

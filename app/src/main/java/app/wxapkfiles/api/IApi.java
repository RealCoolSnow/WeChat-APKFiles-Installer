package app.wxapkfiles.api;

import app.network.bean.BaseResp;
import app.wxapkfiles.api.bean.resp.HelloResp;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * File: IApi
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/2 17:15
 * Description:
 */
public interface IApi {
    @POST("hello")
    Observable<BaseResp<HelloResp>> hello(@Body RequestBody body);
}

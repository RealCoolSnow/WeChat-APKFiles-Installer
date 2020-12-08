package app.wxapkfiles.api;

import app.network.bean.BaseResp;
import app.wxapkfiles.api.bean.resp.UpdateResp;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * File: IApi
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/2 17:15
 * Description:
 */
public interface IApi {
    @GET("wxfileinstall_update")
    Observable<BaseResp<UpdateResp>> checkUpdate();
}

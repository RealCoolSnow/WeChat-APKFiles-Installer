package app.network.bean;

import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * File: Request
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/2 17:11
 * Description:
 */
public class BaseReq {
    public String toJson() {
        return new Gson().toJson(this);
    }

    public RequestBody toRequestBody() {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), toJson());
    }
}

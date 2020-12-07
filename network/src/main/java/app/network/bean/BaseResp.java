package app.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * File: Response
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/10/30 18:19
 * Description:
 */
public class BaseResp<T> {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

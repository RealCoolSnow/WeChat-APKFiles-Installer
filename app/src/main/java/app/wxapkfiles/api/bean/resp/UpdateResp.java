package app.wxapkfiles.api.bean.resp;

import app.network.bean.BaseResp;

/**
 * File: UpdateResp
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/8 16:34
 * Description:
 */
public class UpdateResp extends BaseResp {
    private int vcode;
    private String vname;
    private String url;
    private String hint;

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

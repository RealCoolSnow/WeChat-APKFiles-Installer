package app.wxapkfiles.util;

import android.content.Context;

import app.wxapkfiles.Application;
import app.wxapkfiles.api.bean.resp.UpdateResp;

/**
 * Created by coolsnow on 2017/3/17.
 */

public class UpdateInfo {
    private UpdateResp updateResp;

    /*
    {"vcode":100,"vname":"1.0.1","url":"http://www.baidu.com","hint":"新版本,请立即升级"}
     */
    public UpdateInfo(Context context, UpdateResp updateResp) {
        this.updateResp = updateResp;
    }

    public boolean isNeedUpdate() {
        int v = VersionUtil.getVersionCode(Application.getInstance());
        if (updateResp != null && v > 0 && updateResp.getVcode() > 0 && updateResp.getVcode() > v) {
            return true;
        }
        return false;
    }
}

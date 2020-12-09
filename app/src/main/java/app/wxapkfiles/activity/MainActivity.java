package app.wxapkfiles.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import app.network.BaseObserver;
import app.network.RxScheduler;
import app.network.bean.BaseResp;
import app.wxapkfiles.R;
import app.wxapkfiles.adapter.ApkAdapter;
import app.wxapkfiles.api.bean.resp.UpdateResp;
import app.wxapkfiles.base.BaseActivity;
import app.wxapkfiles.bean.ApkInfo;
import app.wxapkfiles.databinding.ActivityMainBinding;
import app.wxapkfiles.network.RetrofitFactory;
import app.wxapkfiles.util.ApkFinder;
import app.wxapkfiles.util.DialogUtil;
import app.wxapkfiles.util.UpdateInfo;
import app.wxapkfiles.util.Util;
import app.wxapkfiles.util.VersionUtil;
import io.reactivex.Observable;
import tool.easypermissions.EasyPermissions;

import static tool.easypermissions.EasyPermissions.hasPermissions;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "MainActivity";
    private static final int RC_ALL_PERMISSIONS = 2000;
    private ApkAdapter apkAdapter;
    private boolean scanning = false;

    @Override
    protected void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apkAdapter = new ApkAdapter(this);
        binding.recyclerView.setAdapter(apkAdapter);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            scanAPK();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }, 800);
        });
        checkUpdate();
        requestPermission();
    }

    private void requestPermission() {
        List<String> permList = new ArrayList<>();
        if (!hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permList.size() > 0) {
            String[] perms = permList.toArray(new String[permList.size()]);
            EasyPermissions.requestPermissions(this, "",
                    RC_ALL_PERMISSIONS, perms);
            Logger.d(TAG, "requestPermissions -> " + perms.length);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            showAbout();
            return true;
        } else if (id == R.id.action_refresh) {
            scanAPK();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        DialogUtil.showMessage(this, getString(R.string.about), getString(R.string.about_text) + VersionUtil.getVersionName(this), getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, null, null);
    }

    private void checkUpdate() {
        Observable<BaseResp<UpdateResp>> observable = RetrofitFactory.getInstance().checkUpdate();
        observable.compose(RxScheduler.compose(this.bindToLifecycle())).subscribe(new BaseObserver<UpdateResp>(this) {
            @Override
            protected void onSuccess(String msg, UpdateResp data) {
                _checkUpdate(data);
            }

            @Override
            protected void onError(int code, String msg) {
            }
        });
    }

    private void _checkUpdate(UpdateResp updateResp) {
        UpdateInfo updateInfo = new UpdateInfo(MainActivity.this, updateResp);
        if (updateInfo.isNeedUpdate()) {
            DialogUtil.showMessage(MainActivity.this, getString(R.string.hint), updateResp.getHint(), getString(R.string.update),
                    (dialog, which) -> Util.open(MainActivity.this, updateResp.getUrl()), getString(R.string.cancel), null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanAPK();
    }

    private void scanAPK() {
        if (!scanning) {
            binding.viewContent.setVisibility(View.GONE);
            binding.viewEmpty.setVisibility(View.GONE);
            new ScanTask().execute();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        Logger.d(TAG, "onRequestPermissionsResult->" + requestCode);
    }

    private class ScanTask extends AsyncTask<Void, Void, List<ApkInfo>> {

        @Override
        protected List<ApkInfo> doInBackground(Void... voids) {
            return new ApkFinder().getFileList(MainActivity.this);
        }

        @Override
        protected void onPostExecute(List<ApkInfo> list) {
            super.onPostExecute(list);
            scanning = false;
            apkAdapter.setList(list);
            apkAdapter.notifyDataSetChanged();
            boolean empty = list.size() <= 0;
            binding.viewEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.viewContent.setVisibility(empty ? View.GONE : View.VISIBLE);
            binding.tvApkCount.setText(String.format(getString(R.string.apk_found_tip), list.size()));
        }
    }
}

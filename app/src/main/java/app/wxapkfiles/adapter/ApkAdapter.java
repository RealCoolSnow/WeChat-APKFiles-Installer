package app.wxapkfiles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.wxapkfiles.R;
import app.wxapkfiles.bean.ApkInfo;
import app.wxapkfiles.util.ApkUtil;
import app.wxapkfiles.util.TimeUtil;

/**
 * File: ApkAdapter
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/7 16:27
 * Description:
 */
public class ApkAdapter extends RecyclerView.Adapter<ApkAdapter.ViewHolder> {
    private List<ApkInfo> apkInfoList = new ArrayList<>();
    private Context context;

    public ApkAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ApkInfo> list) {
        apkInfoList.clear();
        apkInfoList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_apkinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApkInfo apkInfo = apkInfoList.get(position);
        holder.name.setText(apkInfo.getAppName());
        holder.icon.setImageDrawable(apkInfo.getIcon());
        holder.version.setText(apkInfo.getVersionName());
        holder.time.setText(TimeUtil.format(apkInfo.getLastModified()));
        holder.itemView.setTag(apkInfo.getPath());
        holder.itemView.setOnClickListener(v -> {
            ApkUtil.install(context, (String) holder.itemView.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return apkInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        private TextView version;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            version = itemView.findViewById(R.id.version);
            time = itemView.findViewById(R.id.time);
        }
    }
}

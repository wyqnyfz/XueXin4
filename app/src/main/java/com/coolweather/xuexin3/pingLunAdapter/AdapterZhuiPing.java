package com.coolweather.xuexin3.pingLunAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.R;

import java.util.List;

public class AdapterZhuiPing extends RecyclerView.Adapter<AdapterZhuiPing.ViewHolder> {


    List<PingLun.RepliesBean> mList;
    private View mView;

    public AdapterZhuiPing(List<PingLun.RepliesBean> list) {
        this.mList = list;
    }
    @NonNull
    @Override
    public AdapterZhuiPing.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhuiping, parent,false);
        ViewHolder viewHolder =new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterZhuiPing.ViewHolder holder, int position) {
        PingLun.RepliesBean p = mList.get(position);          //获取第pos个信息
        if(p.getPhoto()!=null){
            Glide.with(mView.getContext()).load(p.getPhoto()).into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.img_login_touxiang);
        }
        String s = p.getLfNickName();
        if(s!=null)
            holder.neck.setText(s);
        s = p.getDate();
        if(s!=null)
            holder.time.setText(s);
        s = p.getContent();
        if(s!=null)
            holder.content.setText(": "+s);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView neck;
        TextView time;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_zhuiping_img);
            neck = itemView.findViewById(R.id.tv_zhuiping_neck);
            time = itemView.findViewById(R.id.tv_zhuiping_time);
            content = itemView.findViewById(R.id.tv_zhuiping_content);

        }
    }
}

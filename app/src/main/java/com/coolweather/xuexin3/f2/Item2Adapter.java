package com.coolweather.xuexin3.f2;


import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.List;

public class Item2Adapter extends RecyclerView.Adapter<Item2Adapter.ViewHolder> {

    List<SeletDatas> mList;
    private View mView;

    public Item2Adapter(List<SeletDatas> list){
        mList = list;
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_f2, parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeletDatas p = mList.get(position);

        if(p.photo!=null){
            Glide.with(mView.getContext()).load(p.photo).into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.img_f1_z1);
        }
        if(p.nickName!=null){
            holder.neckname.setText(p.nickName);
        }else {
            holder.neckname.setText("还没有昵称");
        }
        if(p.nickName!=null){
            holder.signature.setText(p.signature);
        }else {
            holder.signature.setText("暂无");
        }

        if(p.nickName!=null){
            holder.time.setText(p.date);
        }else {
            holder.time.setText("暂无");
        }

        if(p.title.length()>10){
            holder.title.setText(p.title.substring(0, 10)+"...");
        }else{
            holder.title.setText(p.title);
        }


        holder.content.setText(Html.fromHtml(p.content, new ImageGetterUtils.MyImageGetter(mView.getContext(), holder.content),null));
        holder.zan.setText(p.likeAmount+"");
        holder.ping.setText(p.leaveAmount+"");
        if(p.likeStatus==1){
            holder.imgDianzan.setImageResource(R.drawable.dianzan1);
        }else {
            holder.imgDianzan.setImageResource(R.drawable.dianzan);
        }
        if (onitemClick != null) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView neckname;
        TextView signature;
        TextView time;
        TextView title;
        TextView content;

        ImageView imgDianzan;
        TextView zan;
        TextView ping;
        LinearLayout mLinearLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_f2_img);
            neckname = itemView.findViewById(R.id.tv_f2_nicheng);
            signature = itemView.findViewById(R.id.tv_f2_say);
            time = itemView.findViewById(R.id.tv_f2_time);
            title = itemView.findViewById(R.id.tv_f2_title);
            content = itemView.findViewById(R.id.tv_f2_content);
            zan = itemView.findViewById(R.id.tv_f2_zan);
            imgDianzan = itemView.findViewById(R.id.img_f2_dianzan);
            ping = itemView.findViewById(R.id.tv_f2_ping);
            mLinearLayout = itemView.findViewById(R.id.ll_f2);

        }
    }
}

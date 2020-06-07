package com.coolweather.xuexin3.f4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.List;

public class ItemC1Aapter extends RecyclerView.Adapter<ItemC1Aapter.ViewHolder> {

    List<SeletDatas> mList;
    private View mView;

    public ItemC1Aapter(List<SeletDatas> list) {
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
    public ItemC1Aapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_c1, parent,false);
        ItemC1Aapter.ViewHolder viewHolder = new ItemC1Aapter.ViewHolder(mView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemC1Aapter.ViewHolder holder, int position) {
        SeletDatas p = mList.get(position);          //获取第pos个信息

        Glide.with(mView.getContext()).load(p.photo).into(holder.touxiang);
        holder.title.setText(p.title);
        holder.time.setText(p.date);
        if(p.content.length()>30){
            holder.content.setText(p.content.substring(0, 30)+"...");
        }else {
            holder.content.setText(p.content);
        }

        String[] imgs = p.photos.split(",");
        if(imgs.length>=3){
            Glide.with(mView.getContext()).load(imgs[2]).into(holder.img3);
        }
        if(imgs.length>=2){
            Glide.with(mView.getContext()).load(imgs[1]).into(holder.img2);
        }
        if(imgs.length>=1){
            Glide.with(mView.getContext()).load(imgs[0]).into(holder.img1);
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

        ImageView touxiang;
        TextView title;
        TextView time;
        TextView content;

        ImageView img1;
        ImageView img2;
        ImageView img3;

        TextView dianzan;
        TextView pinglun;
        TextView zhuanfa;

        LinearLayout mLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            touxiang = itemView.findViewById(R.id.img_ci1_img);
            time = itemView.findViewById(R.id.tv_ci1_time);
            title = itemView.findViewById(R.id.tv_ci1_title);
            content = itemView.findViewById(R.id.tv_ci1_content);

            img1 = itemView.findViewById(R.id.iv_ci1_img1);
            img2 = itemView.findViewById(R.id.iv_ci1_img2);
            img3 = itemView.findViewById(R.id.iv_ci1_img3);

            dianzan = itemView.findViewById(R.id.tv_ci1_like);
            pinglun = itemView.findViewById(R.id.tv_ci1_take);
            zhuanfa = itemView.findViewById(R.id.tv_ci1_send);

            mLinearLayout = itemView.findViewById(R.id.ll_ci1);
        }
    }
}

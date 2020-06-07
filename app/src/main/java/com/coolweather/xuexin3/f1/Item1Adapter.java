package com.coolweather.xuexin3.f1;

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


public class Item1Adapter extends RecyclerView.Adapter<Item1Adapter.ViewHolder> {

    List<SeletDatas> mList;
    private View mView;

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }



    public Item1Adapter(List<SeletDatas> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_f1, parent,false);
        ViewHolder viewHolder =new ViewHolder(mView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeletDatas p = mList.get(position);          //获取第pos个信息

        if(p.photos!=null){
            Glide.with(mView.getContext()).load(p.photos).into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.img_f1_z1);
        }
        if(p.title.length()>10){
            holder.title.setText(p.title.substring(0, 10)+"...");
        }else{
            holder.title.setText(p.title);
        }

        holder.content.setText(Html.fromHtml(p.content, new ImageGetterUtils.MyImageGetter(mView.getContext(), holder.content),null));

        holder.zan.setText(p.likeAmount+"");
        holder.ping.setText(p.leaveAmount+"");
        holder.neckname.setText(p.nickName);
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
        TextView title;
        TextView content;
        TextView zan;
        TextView ping;
        TextView neckname;
        LinearLayout mLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_i1_img);
            title = itemView.findViewById(R.id.tv_i1_title);
            content = itemView.findViewById(R.id.tv_i1_content);
            zan = itemView.findViewById(R.id.tv_i1_zan);
            ping = itemView.findViewById(R.id.tv_i1_ping);
            neckname = itemView.findViewById(R.id.tv_i1_neckname);
            mLinearLayout = itemView.findViewById(R.id.ll_f1);

        }
    }
}


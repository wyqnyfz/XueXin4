package com.coolweather.xuexin3.artile;

import android.text.Html;
import android.util.Log;
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

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {


    List<SeletDatas> mList;
    private String TAG = "AdapterArticle";
    private View mView;

    public AdapterArticle(List<SeletDatas> list) {
        mList = list;
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent,false);
        ViewHolder viewHolder =new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeletDatas p = mList.get(position);          //获取第pos个信息

        Log.d(TAG+"--------", p.photos);
        if(p.photo!=null){
            Glide.with(mView.getContext()).load(p.photo).into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.img_f1_z1);
        }
        if(p.title.length()>10){
            holder.title.setText(p.title.substring(0, 15)+"...");
        }else{
            holder.title.setText(p.title);
        }
        holder.content.setText(Html.fromHtml(p.content, new ImageGetterUtils.MyImageGetter(mView.getContext(), holder.content),null));
        holder.zan.setText(p.likeAmount+"");
        holder.ping.setText(p.leaveAmount+"");

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
        LinearLayout mLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_ia_img);
            title = itemView.findViewById(R.id.tv_ia_title);
            content = itemView.findViewById(R.id.tv_ia_content);
            zan = itemView.findViewById(R.id.tv_ia_zan);
            ping = itemView.findViewById(R.id.tv_ia_ping);
            mLinearLayout = itemView.findViewById(R.id.ll_ia);
        }
    }
}

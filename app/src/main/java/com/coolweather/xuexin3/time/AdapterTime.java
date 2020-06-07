package com.coolweather.xuexin3.time;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.List;

public class AdapterTime extends RecyclerView.Adapter<AdapterTime.ViewHolder> {

    List<SeletDatas> mList;
    private View mView;

    public AdapterTime(List<SeletDatas> list) {
        mList = list;
    }


    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position, View v);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent,false);
        ViewHolder viewHolder =new ViewHolder(mView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeletDatas seletDatas = mList.get(position);
        holder.title.setText(seletDatas.title);
        if(position%2==0){
            holder.lyLeft.setVisibility(View.VISIBLE);
            holder.lyRight.setVisibility(View.INVISIBLE);
            holder.timel1.setText("3.1-3.25");
            holder.timel2.setText("4.1-4.25");
            holder.timel3.setText("5.1-5.25");
            holder.timel4.setText("6.1-6.25");
        }else {
            holder.lyLeft.setVisibility(View.INVISIBLE);
            holder.lyRight.setVisibility(View.VISIBLE);
            holder.timer1.setText("3.1-3.25");
            holder.timer2.setText("4.1-4.25");
            holder.timer3.setText("5.1-5.25");
            holder.timer4.setText("6.1-6.25");
        }

        if (onitemClick != null) {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position,v);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lyLeft;
        LinearLayout lyRight;
        TextView title;
        TextView timel1;
        TextView timel2;
        TextView timel3;
        TextView timel4;
        TextView timer1;
        TextView timer2;
        TextView timer3;
        TextView timer4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lyLeft = itemView.findViewById(R.id.ll_time_left);
            lyRight = itemView.findViewById(R.id.ll_time_right);
            title = itemView.findViewById(R.id.tv_time_title);
            timel1 = itemView.findViewById(R.id.tv_time_l1);
            timel2 = itemView.findViewById(R.id.tv_time_l2);
            timel3 = itemView.findViewById(R.id.tv_time_l3);
            timel4 = itemView.findViewById(R.id.tv_time_l4);
            timer1 = itemView.findViewById(R.id.tv_time_r1);
            timer2 = itemView.findViewById(R.id.tv_time_r2);
            timer3 = itemView.findViewById(R.id.tv_time_r3);
            timer4 = itemView.findViewById(R.id.tv_time_r4);
        }
    }
}

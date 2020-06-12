package com.coolweather.xuexin3.socket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChatMessageAdapter extends BaseAdapter {
    List<ChatMessage> mChatMessageList;
    LayoutInflater inflater;
    Context context;
    String imgContact;
    String imgMyself;

    public ChatMessageAdapter(Context context, List<ChatMessage> list) {
        this.mChatMessageList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ChatMessageAdapter(Context context, List<ChatMessage> list, String imgContact, String imgMyself) {
        this.mChatMessageList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.imgContact = imgContact;
        this.imgMyself = imgMyself;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatMessageList.get(position).getFromUserId() == MyData.sBasicData.id)
            return 0;// 返回的数据位角标
        else
            return 1;
    }

    @Override
    public int getCount() {
        return mChatMessageList.size();
    }


    @Override
    public Object getItem(int i) {
        return mChatMessageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage mChatMessage = mChatMessageList.get(i);
        String content = mChatMessage.getContent();
        String time = formatTime(mChatMessage.getDateTime());
        int fromId = mChatMessage.getFromUserId().intValue();
        int toId = mChatMessage.getToUserId().intValue();


        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            if (fromId != MyData.sBasicData.id) {//对方发送
                view = inflater.inflate(R.layout.item_chat_receive_text, viewGroup, false);
                holder.tv_content = view.findViewById(R.id.tv_content);
                holder.tv_sendTime = view.findViewById(R.id.tv_sendtime);
                holder.tv_display_name = view.findViewById(R.id.tv_display_name);
                holder.ivHeadPortrait = view.findViewById(R.id.jmui_avatar_iv_contract);

                if(!imgContact.equals("") && imgContact!=null){
                    Glide.with(context)
                            .load(imgContact)
                            .into(holder.ivHeadPortrait);
                }

            } else {
                view = inflater.inflate(R.layout.item_chat_send_text, viewGroup, false);
                holder.tv_content = view.findViewById(R.id.tv_content);
                holder.tv_sendTime = view.findViewById(R.id.tv_sendtime);
                holder.tv_isRead = view.findViewById(R.id.tv_isRead);
                holder.ivHeadPortrait = view.findViewById(R.id.jmui_avatar_iv);

                if(!imgMyself.equals("") && imgMyself!=null){
                    Glide.with(context)
                            .load(imgMyself)
                            .into(holder.ivHeadPortrait);
                }
            }

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_sendTime.setText(time);
        holder.tv_content.setVisibility(View.VISIBLE);
        holder.tv_content.setText(content);

        return view;
    }

    class ViewHolder {
        private TextView tv_content, tv_sendTime, tv_display_name, tv_isRead;
        private ImageView ivHeadPortrait;
    }


    /**
     * 将毫秒数转为日期格式
     *
     * @param timeMillis
     * @return
     */
    private String formatTime(String timeMillis) {
        long time= Long.parseLong(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }
}

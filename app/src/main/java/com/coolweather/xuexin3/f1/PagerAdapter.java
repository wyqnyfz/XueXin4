package com.coolweather.xuexin3.f1;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.List;

//轮番图Adapter
public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private static final String TAG = "PagerAdapter";
    private List<Integer> mList = null;

    public PagerAdapter(List<Integer> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        if(mList!=null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int p = position % mList.size();
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.d(TAG, "position"+position);
        imageView.setImageResource(mList.get(p));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }
}

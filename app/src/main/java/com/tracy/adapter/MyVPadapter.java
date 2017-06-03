package com.tracy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trcay.weilinsong.R;

import java.util.ArrayList;

/**
 * Created by trcay on 2017/4/16.
 */
public class MyVPadapter extends PagerAdapter {

    int[] imgID = {R.mipmap.imgviewpager, R.mipmap.heiker};
    Context context = null;
    ImageView img;
    ArrayList<View> views = new ArrayList<View>();


    public MyVPadapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
        for (int i = 0; i < imgID.length; i++) {
            img = new ImageView(context);
            img.setImageResource(imgID[i]);
            views.add(img);
        }

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        notifyDataSetChanged();
        return views.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        notifyDataSetChanged();
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

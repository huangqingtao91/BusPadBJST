package com.ltime.buspad.mymusic;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.bean.MusicResult;

import java.util.List;

/**
 * Created by YF001 on 2018/7/28.
 */

public class MyMusicAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<MusicResult.DataBean> mDatas;
    private int mCurrentItem = -1;

    private ObjectAnimator animator;

    public MyMusicAdapter(Context context, List<MusicResult.DataBean> list) {
        this.mContext = context;
        this.mDatas = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (!isDataEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (isDataEmpty())
            return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (isDataEmpty())
            return 0;
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.mymusix_item, parent, false);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_art = (TextView) convertView.findViewById(R.id.tv_art);

            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);

            convertView.setTag(holder);

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_name.setText(mDatas.get(position).getTrackName());
        holder.tv_art .setText(mDatas.get(position).getTrackArtist());

//        final ViewHolder finalHolder = holder;
//        Glide.with(mContext)//圆角
//                .load(Constants.URL+mDatas.get(position).getThumb())
//                .asBitmap()  //这句不能少，否则下面的方法会报错
//                .centerCrop()
//                .into(new BitmapImageViewTarget(finalHolder.iv_pic) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        finalHolder.iv_pic.setImageDrawable(circularBitmapDrawable);
//                    }
//                });

        animator = ObjectAnimator.ofFloat(holder.iv_pic, "rotation", 0f, 360.0f);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(-1);//设置动画重复次数（-1代表一直转）
        animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
//        animator.start();

        if (mCurrentItem == position) {
            animator.start();
            holder.tv_name.setTextColor(Color.parseColor("#648bf7"));
            holder.tv_id.setTextColor(Color.parseColor("#648bf7"));
            holder.tv_art.setTextColor(Color.parseColor("#648bf7"));
        } else {
            animator.pause();
            holder.tv_name.setTextColor(Color.parseColor("#000000"));
            holder.tv_id.setTextColor(Color.parseColor("#000000"));
            holder.tv_art.setTextColor(Color.parseColor("#1D1D1D"));
        }

        return convertView;
    }


    ///////////////获取歌曲时常///////////////////////


    public void setCurrentItem(int currentItem) {
        mCurrentItem = currentItem;
    }

    private boolean isDataEmpty() {
        if (mDatas != null && mDatas.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

   static class ViewHolder {

        TextView tv_id, tv_name,tv_art;

         ImageView iv_pic;


    }
}

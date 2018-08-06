package com.ltime.buspad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.VideoResult;

import java.util.List;


/**
 * Created by YF001 on 2018/4/28.
 */

public class ListVideoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<VideoResult.DataBean> mDatas;

    public ListVideoAdapter(Context context, List<VideoResult.DataBean> list) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_video, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.rl = convertView.findViewById(R.id.rl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        if (position < 2) {
//            holder.rl.setVisibility(View.VISIBLE);
//        }

        holder.mTextView.setText(mDatas.get(position).getFilename());
        Glide.with(mContext).load(mDatas.get(position).getFileimg()).into(holder.mImageView);  //非圆角

        return convertView;
    }

    private boolean isDataEmpty() {
        if (mDatas != null && mDatas.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    class ViewHolder {
        RelativeLayout rl;
        TextView mTextView;
        ImageView mImageView;
    }

}

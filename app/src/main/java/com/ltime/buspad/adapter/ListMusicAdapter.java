package com.ltime.buspad.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.bean.MusicResult;

import java.util.List;


/**
 * Created by YF001 on 2018/4/28.
 */

public class ListMusicAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MusicResult.DataBean> mDatas;
    public static int mCurrentItem = 0;

    public ListMusicAdapter(Context context, List<MusicResult.DataBean> list) {
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
            convertView = mInflater.inflate(R.layout.item_music, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mDatas.get(position).getTrackName());

        if (mCurrentItem == position) {
            holder.mTextView.setTextColor(Color.parseColor("#ff6600"));
        } else {
            holder.mTextView.setTextColor(Color.parseColor("#000000"));
        }

        return convertView;
    }

    private boolean isDataEmpty() {
        if (mDatas != null && mDatas.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void setCurrentItem(int currentItem) {
        this.mCurrentItem = currentItem;
    }

    class ViewHolder {

        TextView mTextView;

    }

}

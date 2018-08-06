package com.ltime.buspad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.EbookResult;

import java.util.List;


/**
 * Created by YF001 on 2018/4/28.
 */

public class ListEbookAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<EbookResult.DataBean> mDatas;

    public ListEbookAdapter(Context context, List<EbookResult.DataBean> list) {
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
            convertView = mInflater.inflate(R.layout.item_ebook, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mDatas.get(position).getTitle());

        if (mDatas.get(position).getType().equals("pdf")) {
            Glide.with(mContext).load(R.mipmap.icon_book).into(holder.mImageView);
        }else {
            Glide.with(mContext).load(R.mipmap.icon_book).into(holder.mImageView);
        }
//        Glide.with(mContext).load(mDatas.get(position).getFileimg()).into(holder.mImageView);


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

        TextView mTextView;
        ImageView mImageView;
    }

}

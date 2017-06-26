package yfwang.androiddemo.adapter.keyboard_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import yfwang.androiddemo.R;


/**
 * Description: GridVeiw适配器
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 10:48
 */
public class KeyBoardAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public KeyBoardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(@NonNull List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_keyboard, null);
            viewHolder = new ViewHolder();
            viewHolder.mTvKey = (TextView) convertView.findViewById(R.id.tv_key);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if ("".equals(mList.get(position))) {
            viewHolder.mTvKey.setBackgroundResource(R.drawable.common_keyboard_unclick_shape);
        } else {
            viewHolder.mTvKey.setVisibility(View.VISIBLE);

        }

        viewHolder.mTvKey.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView mTvKey;
    }


}

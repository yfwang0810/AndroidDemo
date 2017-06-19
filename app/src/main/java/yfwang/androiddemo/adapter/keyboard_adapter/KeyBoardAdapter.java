package yfwang.androiddemo.adapter.keyboard_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import yfwang.androiddemo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 10:48
 */
public class KeyBoardAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, String>> mList;

    public KeyBoardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(@NonNull List<Map<String, String>> mList) {
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
            convertView = View.inflate(mContext, R.layout.keyboard_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mTvKey = (TextView) convertView.findViewById(R.id.tv_key);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 11) {
            viewHolder.mTvKey.setVisibility(View.INVISIBLE);
            viewHolder.mTvKey.setBackgroundColor(Color.parseColor("#f2f4f7"));
        } else {
            viewHolder.mTvKey.setVisibility(View.VISIBLE);
        }
        viewHolder.mTvKey.setText(mList.get(position).get("name"));
        return convertView;
    }

    class ViewHolder {
        TextView mTvKey;
    }


}

package yfwang.androiddemo.adapter.timepicker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yfwang.androiddemo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/2/21 13:22
 */
public class GalleryAdapter extends BaseAdapter {

    private int selectPosition;
    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;

    public GalleryAdapter(LayoutInflater mInflater, Context context){
        mData = new ArrayList<>();
        this.mInflater = mInflater;
        this.context = context;
    }
    public void setData(int selectPosition,List<String> mData){
        this.selectPosition = selectPosition;
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.time_line_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_numbers);
            holder.centerLine = convertView.findViewById(R.id.tv_line);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mData.get(position));
        if (selectPosition == position) {
            holder.textView.setActivated(true);
            holder.centerLine.setBackgroundColor(Color.parseColor("#0094f6"));
        }
        else {
            holder.textView.setActivated(false);
            holder.centerLine.setBackgroundColor(Color.parseColor("#36393a"));
        }

        return convertView;
    }

    class ViewHolder {
        TextView textView = null;
        View centerLine = null;
    }
}

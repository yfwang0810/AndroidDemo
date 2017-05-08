package yfwang.androiddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yfwang.androiddemo.R;
import yfwang.androiddemo.bean.DemoInfo;

/**
 * Description: demo列表
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/4/8 10:59
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context mContext;
    private List<DemoInfo> mDemoInfoList;

    public ListAdapter(List<DemoInfo> mDemoInfoList) {
        this.mDemoInfoList = mDemoInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DemoInfo demoInfo = mDemoInfoList.get(position);
                Intent intent = new Intent(mContext, demoInfo.getDemoClass());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoInfo demoInfo = mDemoInfoList.get(position);
        holder.demoName.setText(demoInfo.getTitle());
        holder.demoDes.setText(demoInfo.getDesc());
    }

    @Override
    public int getItemCount() {
        return mDemoInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        @BindView(R.id.demo_des)
        TextView demoDes;
        @BindView(R.id.demo_name)
        TextView demoName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView = (CardView) view;
        }
    }
}

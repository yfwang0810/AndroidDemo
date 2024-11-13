package yfwang.androiddemo.adapter.share_element_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import yfwang.androiddemo.R;

/**
 * Description:  共享元素Adapter
 * Copyright  : Copyright (c) 2016
 * Author     : yafei.wang
 * Date       : 2017/3/8 15:28
 */
public class DataInfoAdapter extends Adapter<DataInfoAdapter.DataViewHolder> {
    private Context mContext;
    private List<Integer> mData;

    public DataInfoAdapter(Context mContext){

        this.mContext = mContext;
    }
    public void setData(List<Integer> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }



    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.mImageView.setImageResource(mData.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{


        private  ImageView mImageView;

        public DataViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }

    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

package yfwang.androiddemo.adapter.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.bean.NearAddressBean;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/23 19:53
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private Context mContext;
    private List<NearAddressBean> mList;
    private OnItemClickListener onItemClickListener;

    public AddressListAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<NearAddressBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.map_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTitle.setText(mList.get(position).getName());
        holder.mAddress.setText(mList.get(position).getAddress());
        if (mList.get(position).isSelect()) {
            holder.mIcon.setSelected(true);
            holder.mTitle.setSelected(true);
            holder.mAddress.setSelected(true);
            holder.mChoise.setVisibility(View.VISIBLE);
        } else {
            holder.mIcon.setSelected(false);
            holder.mTitle.setSelected(false);
            holder.mAddress.setSelected(false);
            holder.mChoise.setVisibility(View.GONE);
        }

        holder.mRelIetm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(mList.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mAddress;
        private ImageView mIcon, mChoise;
        private RelativeLayout mRelIetm;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mAddress = (TextView) itemView.findViewById(R.id.tv_addrdss);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mChoise = (ImageView) itemView.findViewById(R.id.iv_choise);
            mRelIetm = (RelativeLayout) itemView.findViewById(R.id.rel_item);

        }
    }

    public void setOnItemclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(NearAddressBean nearAddressBean, int position);
    }

}

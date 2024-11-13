package yfwang.androiddemo.adapter.popwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.bean.FilterBean;
import yfwang.androiddemo.widget.popupwindow.EasyPopupWindow;

/**
 * Bubble popupWindow Adapter
 * Created by ShineF on 2017/7/19 0019.
 */

public class BubblePopAdapter extends BaseAdapter {

    private Context mContext;
    private EasyPopupWindow mEasyPopupWindow;
    List<FilterBean> mList;
    ViewHolder holder;
    private OnPopListClickListener mListener;

    public BubblePopAdapter(Context context, EasyPopupWindow easyPopupWindow, List<FilterBean> list) {
        this.mContext = context;
        this.mEasyPopupWindow = easyPopupWindow;
        this.mList = list;
    }

    public void setOnPopListClickListener(OnPopListClickListener listener) {
        this.mListener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.view_bubble_pop_adapter_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterBean filterBean = mList.get(position);

        holder.mTvContent.setText(filterBean.getText());

        if (filterBean.isSelect()) {
            holder.mChoiceImg.setVisibility(View.VISIBLE);
        } else {
            holder.mChoiceImg.setVisibility(View.GONE);
        }

        if (position == mList.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }

        holder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEasyPopupWindow != null && mEasyPopupWindow.isShowing()) {
                    mEasyPopupWindow.dismiss();
                }

                if (mListener != null)
                    mListener.setOnItemClickListener(position, v);
            }
        });
        return convertView;
    }

    public interface OnPopListClickListener {
        void setOnItemClickListener(int position, View view);
    }

    class ViewHolder {
        TextView mTvContent;
        ImageView mChoiceImg;
        RelativeLayout mRlItem;
        View line;

        public ViewHolder(View view) {
            mTvContent = (TextView) view.findViewById(R.id.tv_content_name);
            mChoiceImg = (ImageView) view.findViewById(R.id.iv_choice);
            mRlItem = (RelativeLayout) view.findViewById(R.id.rl_pop_item);
            line = view.findViewById(R.id.line1);
        }
    }
}

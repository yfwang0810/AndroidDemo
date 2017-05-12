package yfwang.androiddemo.adapter.draglayout_adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.bean.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {


    public interface OnStartDragListener {

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    private final Context context;
    public static List<Item> itemList = new ArrayList<>();
    private final OnStartDragListener dragStartListener;

    public ItemAdapter(Context context, OnStartDragListener dragStartListener) {
        this.context = context;
        this.dragStartListener = dragStartListener;

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        final Item item =new Item();
        item.setItemName(itemList.get(position).getItemName());

        notifyItemRemoved(position);
        itemList.remove(position);
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getItemName());
        itemViewHolder.relativeReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(itemViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_adapter, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {

        protected RelativeLayout container;
        protected TextView       tvItemName;
        protected ImageView      ivReorder;
        protected RelativeLayout relativeReorder;


        public ItemViewHolder(final View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container);
            tvItemName = (TextView) v.findViewById(R.id.tvItemName);
            ivReorder = (ImageView) v.findViewById(R.id.ivReorder);
            relativeReorder = (RelativeLayout) v.findViewById(R.id.relativeReorder);
        }

        @Override
        public void onClick(View view) {
        }


        @Override
        public void onItemSelected(Context context) {
            container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        @Override
        public void onItemClear(Context context) {
            container.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

}

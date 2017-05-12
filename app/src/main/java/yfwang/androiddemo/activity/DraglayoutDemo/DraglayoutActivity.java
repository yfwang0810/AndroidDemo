package yfwang.androiddemo.activity.DraglayoutDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.draglayout_adapter.ItemAdapter;
import yfwang.androiddemo.adapter.draglayout_adapter.SimpleItemTouchHelperCallback;
import yfwang.androiddemo.bean.Item;

public class DraglayoutActivity extends AppCompatActivity implements ItemAdapter.OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draglayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        final ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(),this);
        recyclerView.setAdapter(itemAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        loadItems();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ItemAdapter.itemList.clear();
    }

    private void loadItems()
    {
        for (int i = 10; i > 0; i--) {
            Item item = new Item();
            item.setItemName("item" + i);
            ItemAdapter.itemList.add(item);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}

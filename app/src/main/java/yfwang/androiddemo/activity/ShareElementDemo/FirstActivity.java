package yfwang.androiddemo.activity.ShareElementDemo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.share_element_adapter.DataInfoAdapter;

public class FirstActivity extends AppCompatActivity implements DataInfoAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        setSupportActionBar(toolbar);

        setUpWindowTrisience();
        setData();
    }

    private void setData() {
        DataInfoAdapter adapter = new DataInfoAdapter(this);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            list.add(R.drawable.shareelement);
        }

        adapter.setData(list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 10;
                outRect.right = 10;
                outRect.bottom = 10;
                outRect.top = 10;
            }
        });
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }


    /**
     * 设置共享元素效果
     */
    private void setUpWindowTrisience() {
        TransitionSet mtransitionset = new TransitionSet();
        mtransitionset.addTransition(new ChangeBounds());
        mtransitionset.addTransition(new ChangeImageTransform());
        mtransitionset.setDuration(450);
//        getWindow().setEnterTransition(mtransitionset);
//        getWindow().setExitTransition(mtransitionset);
        getWindow().setSharedElementEnterTransition(mtransitionset);
        getWindow().setSharedElementExitTransition(mtransitionset);
        getWindow().setAllowEnterTransitionOverlap(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view,int position) {
        ActivityOptionsCompat aop= ActivityOptionsCompat.makeSceneTransitionAnimation(FirstActivity.this,
                view.findViewById(R.id.iv_image),"image");
        Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
        ActivityCompat.startActivity(FirstActivity.this,intent,aop.toBundle());
    }
}

package yfwang.androiddemo.activity.PhotoViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import yfwang.androiddemo.R;
import yfwang.androiddemo.widget.photoview.HackyViewPager;
import yfwang.androiddemo.widget.photoview.PhotoView;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/30 13:11
 */
public class ListPicActivity extends Activity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        mViewPager.setAdapter(new SamplePagerAdapter());

    }

    private class SamplePagerAdapter extends PagerAdapter {

        private int[] drawables = {R.drawable.yf, R.drawable.yf, R.drawable.yf,
                R.drawable.yf, R.drawable.yf, R.drawable.yf};

        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(drawables[position]);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}

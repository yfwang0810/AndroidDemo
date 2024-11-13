package yfwang.androiddemo.activity.PhotoViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import yfwang.androiddemo.R;
import yfwang.androiddemo.widget.photoview.PhotoViewAttacher;
import yfwang.androiddemo.widget.popupwindow.EasyPopupWindow;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/29 19:43
 */
public class SinglePicActivity extends Activity implements EasyPopupWindow.ChildClickListener, View.OnClickListener {

    private ImageView mPic;
    private PhotoViewAttacher photoViewAttacher;
    private EasyPopupWindow mEasyPopupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepic);
        init();


    }

    private void init() {
        mPic = (ImageView) findViewById(R.id.iv_pic);
        photoViewAttacher = new PhotoViewAttacher(mPic);
        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mEasyPopupWindow = new EasyPopupWindow.Builder(SinglePicActivity.this).setView(R.layout.view_image_popupwindow)
                        .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //设置宽高
                        .setBackgroundAlpha(0.7f) //设置背景透明度
                        .setAnim(R.style.anim_menu_bottom_bar) //设置动画
                        .setOutsideTouchHide(true) //点击外面是否关闭
                        .setOnChildClickListener(SinglePicActivity.this).create(); //布局中的事件回调


                return true;
            }
        });


    }

    @Override
    public void getChildView(int layoutId, View view) {
        Button deletePhoto = (Button) view.findViewById(R.id.btn_delete_photo);
        Button savePhoto = (Button) view.findViewById(R.id.btn_save_photo);
        Button cancel = (Button) view.findViewById(R.id.btn_cancel);

        deletePhoto.setOnClickListener(this);
        savePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            if (mEasyPopupWindow != null && mEasyPopupWindow.isShowing()) {
                mEasyPopupWindow.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        photoViewAttacher.cleanup();
    }
}

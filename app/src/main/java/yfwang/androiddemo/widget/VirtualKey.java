package yfwang.androiddemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.keyboard_adapter.KeyBoardAdapter;

/**
 * Description: 自定义虚拟键盘
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 10:20
 */
public class VirtualKey extends RelativeLayout {

     Context mContext;


    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;
    private KeyBoardAdapter adapter;


    public VirtualKey(Context context) {
        this(context,null);
    }

    public VirtualKey(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VirtualKey(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);

        valueList = new ArrayList<>();

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        initValueList();
        initAdapter();

        addView(view);
    }

    private void initAdapter() {

        adapter = new KeyBoardAdapter(mContext);
        adapter.setData(valueList);
        gridView.setAdapter(adapter);

    }

    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "X");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }

    }
    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }
    public GridView getGridView() {
        return gridView;
    }
}

package yfwang.androiddemo.widget.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.keyboard_adapter.KeyBoardAdapter;

/**
 * Description: 自定义虚拟键盘按键
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/20 9:41
 */
public class VirtualKey extends RelativeLayout {

    Context mContext;
    /**
     * 身份证键盘
     */
    public static int IDNUMBER = 0X000001;
    /**
     * 小数键盘
     */
    public static int FLOATNUMBER = 0X000002;
    /**
     * 数字键盘
     */
    public static int NUMBER = 0X000003;

    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<String> valueList;
    private KeyBoardAdapter adapter;


    public VirtualKey(Context context) {
        this(context, null);
    }

    public VirtualKey(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VirtualKey(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);

        valueList = new ArrayList<>();

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

//        initValueList(IDNUMBER);

        addView(view);
    }

    private void initAdapter() {

        adapter = new KeyBoardAdapter(mContext);
        adapter.setData(valueList);
        gridView.setAdapter(adapter);

    }

    // 初始化按钮上应该显示的数字
    public void initValueList(int type) {
        if (type == IDNUMBER) {
            for (int i = 1; i < 13; i++) {
                if (i < 10) {
                    valueList.add(String.valueOf(i));
                } else if (i == 10) {
                    valueList.add("X");
                } else if (i == 11) {
                    valueList.add(String.valueOf(0));
                } else if (i == 12) {
                    valueList.add("");
                }
            }
        } else if (type == FLOATNUMBER) {
            for (int i = 1; i < 13; i++) {
                if (i < 10) {
                    valueList.add(String.valueOf(i));
                } else if (i == 10) {
                    valueList.add("");
                } else if (i == 11) {
                    valueList.add(String.valueOf(0));
                } else if (i == 12) {
                    valueList.add(".");
                }
            }
        } else if (type == NUMBER) {
            for (int i = 1; i < 13; i++) {
                if (i < 10) {
                    valueList.add(String.valueOf(i));
                } else if (i == 10) {
                    valueList.add("");
                } else if (i == 11) {
                    valueList.add(String.valueOf(0));
                } else if (i == 12) {
                    valueList.add("");
                }
            }

        }
        initAdapter();

    }

    public ArrayList<String> getValueList() {
        return valueList;
    }

    public GridView getGridView() {
        return gridView;
    }

}

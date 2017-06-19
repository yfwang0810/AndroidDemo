package yfwang.androiddemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import yfwang.androiddemo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 14:49
 */
public class VirtualKeyboardView extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    Context mContext;
    private VirtualKey mVirtualKey;
    private ImageView mDelete;
    private TextView mConfirm;
    private GridView gridView;
    private ArrayList<Map<String, String>> mValueList;
    private EditText editText;
    private OnConfirmListener onConfirmListener;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        View view = View.inflate(context, R.layout.keyboard, null);
        mVirtualKey = (VirtualKey) view.findViewById(R.id.vk_key);
        mDelete = (ImageView) view.findViewById(R.id.iv_delete);
        mConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        gridView = mVirtualKey.getGridView();
        mValueList = mVirtualKey.getValueList();
        mDelete.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        addView(view);
    }

    public void setEditText(@NonNull EditText editText) {
        this.editText = editText;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_confirm) {
            if (onConfirmListener!=null){
                onConfirmListener.confirm();
            }
        } else if (v.getId() == R.id.iv_delete) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        if (!"".equals(mValueList.get(position).get("name"))) {
            editable.insert(start, mValueList.get(position).get("name"));
        }

    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener){
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener{
       void confirm();
    }

}

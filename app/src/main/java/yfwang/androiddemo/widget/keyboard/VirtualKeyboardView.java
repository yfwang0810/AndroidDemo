package yfwang.androiddemo.widget.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import yfwang.androiddemo.R;


/**
 * Description: 自定义虚拟键盘
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 14:49
 */
public class VirtualKeyboardView extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnLongClickListener {
    Context mContext;
    private VirtualKey mVirtualKey;
    private ImageView mDelete;
    private TextView mConfirm;
    private GridView gridView;
    private ArrayList<String> mValueList;
    private EditText editText;
    private OnConfirmListener onConfirmListener;

    private Animation enterAnim;

    private Animation exitAnim;

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
        mDelete.setOnLongClickListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VirtualKeyboardView);
        if (typedArray != null) {
            int type = typedArray.getInteger(R.styleable.VirtualKeyboardView_type, 0);
            if (type == 0) {
                //身份证键盘
                mVirtualKey.initValueList(VirtualKey.IDNUMBER);
            } else if (type == 1) {
                //小数键盘
                mVirtualKey.initValueList(VirtualKey.FLOATNUMBER);
            } else if (type == 2) {
                //数字键盘
                mVirtualKey.initValueList(VirtualKey.NUMBER);
            }

            mVirtualKey.invalidate();

            typedArray.recycle();
        }

        initAnim();

        addView(view);
    }

    public void setEditText(@NonNull EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_confirm) {
            if (onConfirmListener != null) {
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

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {

        enterAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_out);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAnimation(enterAnim);
        } else if (visibility == GONE) {
            startAnimation(exitAnim);
        } else if (visibility == INVISIBLE) {
            startAnimation(exitAnim);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        if (!"".equals(mValueList.get(position))) {
            editable.insert(start, mValueList.get(position));
        }

    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.iv_delete) {
            editText.setText("");
        }
        return true;
    }

    public interface OnConfirmListener {
        void confirm();
    }

}

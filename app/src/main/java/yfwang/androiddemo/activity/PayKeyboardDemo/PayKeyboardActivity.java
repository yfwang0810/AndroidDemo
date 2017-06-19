package yfwang.androiddemo.activity.PayKeyboardDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import yfwang.androiddemo.R;
import yfwang.androiddemo.widget.VirtualKeyboardView;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/19 9:33
 */
public class PayKeyboardActivity extends Activity {
    @BindView(R.id.et_number)
    EditText mNumber;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;

    private Animation enterAnim;

    private Animation exitAnim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paykeyboard);
        ButterKnife.bind(this);
        initAnim();
        initView();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {

        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }


    private void initView() {

        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            mNumber.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mNumber, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        virtualKeyboardView.setEditText(mNumber);
        virtualKeyboardView.setOnConfirmListener(new VirtualKeyboardView.OnConfirmListener() {
            @Override
            public void confirm() {
                if (mNumber.getVisibility() == View.VISIBLE) {
                    virtualKeyboardView.startAnimation(exitAnim);
                    virtualKeyboardView.setVisibility(View.GONE);
                }
            }
        });
        mNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);
                if (virtualKeyboardView.getVisibility() != View.VISIBLE) {
                    virtualKeyboardView.startAnimation(enterAnim);
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}

package yfwang.androiddemo.activity.PayKeyboardDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

import yfwang.androiddemo.R;
import yfwang.androiddemo.widget.keyboard.VirtualKeyboardView;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/20 9:35
 */
public class PayKeyboardActivity extends Activity {


    private EditText mNumber;
    private EditText mNumber1;
    private VirtualKeyboardView virtualKeyboardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paykeyboard);
        initView();
    }


    private void initView() {
        mNumber = (EditText) findViewById(R.id.et_number);
        mNumber1 = (EditText) findViewById(R.id.et_number1);
        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        hideSystemKeyboard(mNumber, mNumber1);


        virtualKeyboardView.setOnConfirmListener(new VirtualKeyboardView.OnConfirmListener() {
            @Override
            public void confirm() {
                if (virtualKeyboardView.getVisibility() == View.VISIBLE) {
                    virtualKeyboardView.setVisibility(View.GONE);
                }
            }
        });

        mNumber.setOnFocusChangeListener(onFocusChangeListener);
        mNumber1.setOnFocusChangeListener(onFocusChangeListener);
        mNumber.setOnClickListener(onClickListener);
        mNumber1.setOnClickListener(onClickListener);

    }

    private void hideSystemKeyboard(EditText... mNumber) {
        for (int i = 0; i < mNumber.length; i++) {
            // 设置不调用系统键盘
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                mNumber[i].setInputType(InputType.TYPE_NULL);
            } else {
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                Class<EditText> clazz = EditText.class;
                Method method;

                try {
                    method = clazz.getMethod("setShowSoftInputOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(mNumber[i], false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    method = clazz.getMethod("setSoftInputShownOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(mNumber[i], false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (v instanceof EditText && hasFocus) {
                virtualKeyboardView.setEditText((EditText) v);
                if (virtualKeyboardView.getVisibility() != View.VISIBLE) {
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof EditText) {
                v.requestFocus();
                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);
                virtualKeyboardView.setEditText((EditText) v);
                if (virtualKeyboardView.getVisibility() != View.VISIBLE) {
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            }

        }
    };

}

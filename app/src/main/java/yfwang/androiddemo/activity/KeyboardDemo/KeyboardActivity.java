package yfwang.androiddemo.activity.KeyboardDemo;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

import yfwang.androiddemo.R;
import yfwang.androiddemo.utils.KeyboardUtil;


/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/14 14:03
 */
public class KeyboardActivity extends Activity {
    public EditText mKeyboard;
    public KeyboardView mKeyboardView;
    private KeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        mKeyboard = (EditText) findViewById(R.id.et_keyboard);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);

        init();

    }

    private void init() {
        this.getWindow().setSoftInputMode

                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus",

                    boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(mKeyboard, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyboardUtil = new KeyboardUtil(KeyboardActivity.this, mKeyboard, mKeyboardView);
        mKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardUtil.showKeyboard();
            }
        });

        mKeyboard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                keyboardUtil.showKeyboard();
            }
        });

    }
}

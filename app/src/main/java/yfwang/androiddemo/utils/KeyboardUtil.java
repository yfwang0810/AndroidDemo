package yfwang.androiddemo.utils;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import yfwang.androiddemo.R;


/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/15 11:29
 */
public class KeyboardUtil {

    private static Activity activity;
    private Keyboard keyboard;
    private  KeyboardView mkeyboardView;
    private EditText edit;


    public KeyboardUtil(Activity mActivity, final EditText edit,KeyboardView mKeyboardView) {
        keyboard = new Keyboard(mActivity, R.xml.number);
        KeyboardUtil.activity = mActivity;
        mkeyboardView = mKeyboardView;
        mkeyboardView.setPreviewEnabled(false);
        this.edit = edit;
        mkeyboardView.setKeyboard(keyboard);
        KeyboardView.OnKeyboardActionListener onKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable = edit.getText();
                int start = edit.getSelectionStart();
                if (primaryCode == Keyboard.KEYCODE_DELETE) {
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                }

            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };
        mkeyboardView.setOnKeyboardActionListener(onKeyboardActionListener);
    }


    public void showKeyboard() {
        int visibility = mkeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mkeyboardView.setVisibility(View.VISIBLE);
        }
    }

    private void hideKeyboard() {
        int visibility = mkeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mkeyboardView.setVisibility(View.GONE);
        }
    }

}

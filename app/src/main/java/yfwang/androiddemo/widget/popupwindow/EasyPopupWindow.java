package yfwang.androiddemo.widget.popupwindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * EasyPopupWindow
 * Created by ShineF on 2017/6/16 0016.
 */

public class EasyPopupWindow extends PopupWindow {

    private EasyPopupController mController;
    private EasyPopupSetting mSetting;

    private EasyPopupWindow(Context context) {
        mSetting = new EasyPopupSetting(context);
        mController = new EasyPopupController(context, this);
    }

    public EasyPopupController getController() {
        return mController;
    }

    @Override
    public int getWidth() {
        return mSetting.getWidth();
    }

    @Override
    public int getHeight() {
        return mSetting.getHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mController.setBackgroundAlpha(1.0f);
    }

    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }

    public interface ChildClickListener {
        void getChildView(int layoutId, View view);
    }

    public static class Builder {
        private EasyPopupSetting mSetting;
        private ChildClickListener mClickListener;

        public Builder(Context context) {
            mSetting = new EasyPopupSetting(context);
        }

        public Builder setView(int layoutId) {
            mSetting.setView(layoutId);
            mSetting.setView(null);
            return this;
        }

        public Builder setView(View view) {
            mSetting.setView(view);
            mSetting.setView(0);
            return this;
        }

        public Builder setBackgroundAlpha(float alpha) {
            mSetting.setAlpha(alpha);
            return this;
        }

        public Builder setSize(int width, int height) {
            mSetting.setWidth(width);
            mSetting.setHeight(height);
            return this;
        }

        public Builder setAnim(int animId) {
            mSetting.setStyleId(animId);
            return this;
        }

        public Builder setOutsideTouchHide(boolean touchHide) {
            mSetting.setOutSizeHide(true);
            return this;
        }

        public Builder setOnChildClickListener(ChildClickListener childClickListener) {
            this.mClickListener = childClickListener;
            return this;
        }

        public EasyPopupWindow create() {
            EasyPopupWindow popupWindow = new EasyPopupWindow(mSetting.getContext());
            mSetting.popupApply(popupWindow.getController());
            if (null != mClickListener && 0 != mSetting.getLayoutId()) {
                mClickListener.getChildView(mSetting.getLayoutId(), popupWindow.getController().getmPopupView());
            }
            measureWidthAndHeight(popupWindow.getController().getmPopupView());
            return popupWindow;
        }
    }

}

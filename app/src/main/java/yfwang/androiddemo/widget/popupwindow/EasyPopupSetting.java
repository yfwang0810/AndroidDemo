package yfwang.androiddemo.widget.popupwindow;

import android.content.Context;
import android.view.View;

/**
 *  EasyPopwindow 设置参数
 * Created by ShineF on 2017/6/16 0016.
 */

public class EasyPopupSetting {

    private int mLayoutId;
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private View mView;
    private float mAlpha;
    private int mStyleId;
    public boolean mOutSizeHide = true;

    public EasyPopupSetting(Context context) {
        this.mContext = context;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public View getView() {
        return mView;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    public void setView(int layoutId){
        this.mLayoutId = layoutId;
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void setAlpha(float mAlpha) {
        this.mAlpha = mAlpha;
    }

    public int getStyleId() {
        return mStyleId;
    }

    public void setStyleId(int mStyleId) {
        this.mStyleId = mStyleId;
    }

    public boolean isOutSizeHide() {
        return mOutSizeHide;
    }

    public void setOutSizeHide(boolean mOutSizeHide) {
        this.mOutSizeHide = mOutSizeHide;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void popupApply(EasyPopupController controller) {
        if (0 == mLayoutId) {
            controller.setView(mView);
        } else if (null == mView) {
            controller.setView(mLayoutId);
        } else {
            throw new IllegalArgumentException("PopupWindow set ContentView error, ContentView is null !");
        }
        controller.setSize(mWidth, mHeight);
        controller.setOutSideTouchHide(mOutSizeHide);
        if (0 != mStyleId) {
            controller.setAnimStyle(mStyleId);
        }
        if (0 != mAlpha) {
            controller.setBackgroundAlpha(mAlpha);
        }
    }

}

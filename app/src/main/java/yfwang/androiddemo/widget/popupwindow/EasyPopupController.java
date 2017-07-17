package yfwang.androiddemo.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopupWindow控制器
 * Created by ShineF on 2017/6/16 0016.
 */

public class EasyPopupController {

    private PopupWindow mPopupWindow = null;
    private int mLayoutId = 0;
    private View mView;
    private View mPopupView = null;
    private Context mContext = null;

    public EasyPopupController(Context context, PopupWindow popupWindow) {
        this.mContext = context;
        this.mPopupWindow = popupWindow;
    }

    public View getmPopupView() {
        return mPopupView;
    }

    public void setView(int layoutId) {
        this.mLayoutId = layoutId;
        mView = null;
        init();
    }

    public void setView(View view) {
        this.mView = view;
        mLayoutId = 0;
        init();
    }

    /**
     * 初始化PopupWindow
     */
    private void init() {
        if (mLayoutId != 0) {
            mPopupView = View.inflate(mContext, mLayoutId, null);
        } else if (mView != null) {
            mPopupView = mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    /**
     * 设置PopupWindow宽高
     *
     * @param width  宽度
     * @param height 高度
     */
    public void setSize(int width, int height) {
        if (width == 0 || height == 0) {
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }

    /**
     * 设置PopupWindow背景透明
     *
     * @param alpha 透明值
     */
    public void setBackgroundAlpha(float alpha) {
        Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = alpha;
        window.setAttributes(params);
    }

    /**
     * 设置PopupWindow Outside是否可点击
     *
     * @param touchHide 是否可点击
     */
    public void setOutSideTouchHide(boolean touchHide) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(touchHide);
        mPopupWindow.setFocusable(touchHide);
    }

    /**
     * 设置PopupWindow动画style
     *
     * @param styleRes 动画ID
     */
    public void setAnimStyle(int styleRes) {
        mPopupWindow.setAnimationStyle(styleRes);
    }

}

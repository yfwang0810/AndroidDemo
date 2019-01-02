package yfwang.androiddemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.popwindow.BubblePopAdapter;
import yfwang.androiddemo.bean.FilterBean;
import yfwang.androiddemo.widget.popupwindow.EasyPopupWindow;


/**
 * 泡泡弹框工具类
 * Created by ShineF on 2017/6/28 0028.
 */

public class PopUpUtils {

    /**
     * 箭头朝上
     *
     * @param anchor  相对的控件
     * @param context 上下文
     */
    public static void showBubbleUp(View anchor, Context context, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_top, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);

        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();

        easyPopupWindow.showAsDropDown(anchor);
    }

    /**
     * 箭头朝左
     *
     * @param anchor  相对的控件
     * @param context 上下文
     */
    public static void showBubbleLeft(View anchor, Context context, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_left, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);

        easyPopupWindow.showAtLocation(anchor, Gravity.LEFT, location[0] + anchor.getWidth(), 0);
    }

    /**
     * 箭头朝下面
     *
     * @param anchor  相对的控件
     * @param context 上下文
     */
    public static void showBubbleDown(View anchor, Context context, List<FilterBean> list, BubblePopAdapter.OnPopListClickListener listener) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_down, null);
        ListView mLvList = (ListView) bubbleView.findViewById(R.id.lv_list);

        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(0.6f)
                .setAnim(R.style.anim_pop_up_down)
                .setOutsideTouchHide(true)
                .create();

        BubblePopAdapter adapter = new BubblePopAdapter(context, easyPopupWindow, list);
        mLvList.setAdapter(adapter);
        adapter.setOnPopListClickListener(listener);

        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT | Gravity.TOP, 12, anchor.getHeight() + DensityUtils.dip2px(context, 20));
    }

    public static void showBubbleDown(View anchor, Context context, List<FilterBean> list, BubblePopAdapter.OnPopListClickListener listener, int x, int y) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_down, null);
        ListView mLvList = (ListView) bubbleView.findViewById(R.id.lv_list);

        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(0.6f)
                .setAnim(R.style.anim_pop_up_down)
                .setOutsideTouchHide(true)
                .create();

        BubblePopAdapter adapter = new BubblePopAdapter(context, easyPopupWindow, list);
        mLvList.setAdapter(adapter);
        adapter.setOnPopListClickListener(listener);

        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT | Gravity.TOP, x, y);
    }

    /**
     * 箭头朝右
     * @param anchor    相对的控件
     * @param context  上下文
     * @param text     文本
     */
    public static void showBubbleRight(View anchor, Context context, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_right, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();
        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT, anchor.getWidth(), 0);

    }

    /**
     * 箭头朝下
     *
     * @param anchor  相对的控件
     * @param context 上下文
     * @param text    内容
     */
    public static void showBubbleBottom(View anchor, Context context, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_bottom, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        easyPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, anchor.getHeight());
    }

    /**
     * 箭头朝下
     *
     * @param anchor  相对的控件
     * @param context 上下文
     * @param text    内容
     */
    public static EasyPopupWindow returnBubbleBottom(View anchor, Context context, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_bottom, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        easyPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, anchor.getHeight());
        return easyPopupWindow;
    }

    /**
     * 弹出业务单位选择弹框
     *
     * @param anchor   相对控件
     * @param context  上下文
     * @param list     集合
     * @param listener 回调
     * @param x        x
     * @param y        y
     */
    public static void showBubbleFunctionUnit(View anchor, Context context, List<FilterBean> list, BubblePopAdapter.OnPopListClickListener listener, int x, int y) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_function_unit, null);
        ListView mLvList = (ListView) bubbleView.findViewById(R.id.lv_list);

        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(0.6f)
                .setAnim(R.style.anim_pop_up_function_unit)
                .setOutsideTouchHide(true)
                .create();

        BubblePopAdapter adapter = new BubblePopAdapter(context, easyPopupWindow, list);
        mLvList.setAdapter(adapter);
        adapter.setOnPopListClickListener(listener);

        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT | Gravity.TOP, x, y);
    }

    /**
     * 查看邀请人弹框
     *
     * @param context          上下文
     * @param anchor           相对的控件
     * @param position         选中的位置
     * @param onChoiceListener 回调
     */
    public static void showBubbleInviteSort(Context context, View anchor, int position, final OnChoiceListener onChoiceListener) {
        showBubbleInviteSort(context, anchor, position, onChoiceListener, null, null, null);
    }

    /**
     * 查看邀请人排序
     *
     * @param context          上下文
     * @param anchor           相对的控件
     * @param position         选中的位置
     * @param onChoiceListener 回调
     */
    public static void showBubbleInviteSort(Context context, View anchor, int position, final OnChoiceListener onChoiceListener, String str1, String str2, String str3) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_invite_sort, null);
        RelativeLayout LLChoice1 = (RelativeLayout) bubbleView.findViewById(R.id.ll_choice1);
        RelativeLayout LLChoice2 = (RelativeLayout) bubbleView.findViewById(R.id.ll_choice2);
        RelativeLayout LLChoice3 = (RelativeLayout) bubbleView.findViewById(R.id.ll_choice3);

        final ImageView imageView1 = (ImageView) bubbleView.findViewById(R.id.choice1);
        final ImageView imageView2 = (ImageView) bubbleView.findViewById(R.id.choice2);
        final ImageView imageView3 = (ImageView) bubbleView.findViewById(R.id.choice3);

        TextView textView1 = (TextView) bubbleView.findViewById(R.id.tv_content1);
        TextView textView2 = (TextView) bubbleView.findViewById(R.id.tv_content2);
        TextView textView3 = (TextView) bubbleView.findViewById(R.id.tv_content3);

        TextView line1 = (TextView) bubbleView.findViewById(R.id.line1);
        TextView line2 = (TextView) bubbleView.findViewById(R.id.line2);

        if (!TextUtils.isEmpty(str1)) {
            textView1.setText(str1);
        } else {
            line1.setVisibility(View.GONE);
            LLChoice1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(str2)) {
            textView2.setText(str2);
        } else {
            line2.setVisibility(View.GONE);
            LLChoice2.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(str3)) {
            textView3.setText(str3);
        } else {
            LLChoice3.setVisibility(View.GONE);
        }

        choice(position, imageView1, imageView2, imageView3);

        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(0.6f)
                .setAnim(R.style.anim_pop_up_down)
                .setOutsideTouchHide(true)
                .create();

        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT | Gravity.TOP, 12, anchor.getHeight() + DensityUtils.dip2px(context, 20));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.ll_choice1) {
                    choice(1, imageView1, imageView2, imageView3);
                    onChoiceListener.onChoice(1);

                } else if (i == R.id.ll_choice2) {
                    choice(2, imageView1, imageView2, imageView3);
                    onChoiceListener.onChoice(2);

                } else if (i == R.id.ll_choice3) {
                    choice(3, imageView1, imageView2, imageView3);
                    onChoiceListener.onChoice(3);

                }

                if (easyPopupWindow != null && easyPopupWindow.isShowing()) {
                    easyPopupWindow.dismiss();
                }
            }
        };

        LLChoice1.setOnClickListener(onClickListener);
        LLChoice2.setOnClickListener(onClickListener);
        LLChoice3.setOnClickListener(onClickListener);
    }

    private static void choice(int position, View v1, View v2, View v3) {
        switch (position) {
            case 1:
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface OnChoiceListener {
        void onChoice(int position);
    }


    public static void showBubbleAuthoritySort(Context context, View anchor, int position, final OnChoiceListener onChoiceListener, int xoffset, int yoffset) {
        View bubbleView = View.inflate(context, R.layout.popup_window_authority_time_sort, null);
        LinearLayout LLChoice2 = (LinearLayout) bubbleView.findViewById(R.id.ll_choice2);
        LinearLayout LLChoice3 = (LinearLayout) bubbleView.findViewById(R.id.ll_choice3);

        final ImageView imageView2 = (ImageView) bubbleView.findViewById(R.id.choice2);
        final ImageView imageView3 = (ImageView) bubbleView.findViewById(R.id.choice3);

        choice(position, imageView2, imageView3);

        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();

        easyPopupWindow.showAsDropDown(anchor, xoffset, yoffset);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.ll_choice2) {
                    choice(1, imageView2, imageView3);
                    onChoiceListener.onChoice(1);

                } else if (i == R.id.ll_choice3) {
                    choice(2, imageView2, imageView3);
                    onChoiceListener.onChoice(2);

                }

                if (easyPopupWindow != null && easyPopupWindow.isShowing()) {
                    easyPopupWindow.dismiss();
                }
            }
        };
        LLChoice2.setOnClickListener(onClickListener);
        LLChoice3.setOnClickListener(onClickListener);
    }

	public static void showBubbleAuthoritySort(Context context, View anchor, int position, final OnChoiceListener onChoiceListener) {
		View bubbleView = View.inflate(context, R.layout.popup_window_authority_time_sort, null);
		LinearLayout LLChoice2 = (LinearLayout) bubbleView.findViewById(R.id.ll_choice2);
		LinearLayout LLChoice3 = (LinearLayout) bubbleView.findViewById(R.id.ll_choice3);

		final ImageView imageView2 = (ImageView) bubbleView.findViewById(R.id.choice2);
		final ImageView imageView3 = (ImageView) bubbleView.findViewById(R.id.choice3);

		choice(position, imageView2, imageView3);

		final EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
				.setView(bubbleView)
				.setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
				.setOutsideTouchHide(true)
				.create();

        easyPopupWindow.showAtLocation(anchor, Gravity.RIGHT | Gravity.TOP, 12, anchor.getHeight() + DensityUtils.dip2px(context, 20));

		View.OnClickListener onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = v.getId();
				if (i == R.id.ll_choice2) {
					choice(1, imageView2, imageView3);
					onChoiceListener.onChoice(1);

				} else if (i == R.id.ll_choice3) {
					choice(2, imageView2, imageView3);
					onChoiceListener.onChoice(2);

				}

				if (easyPopupWindow != null && easyPopupWindow.isShowing()) {
					easyPopupWindow.dismiss();
				}
			}
		};
		LLChoice2.setOnClickListener(onClickListener);
		LLChoice3.setOnClickListener(onClickListener);
	}

    private static void choice(int position, View v2, View v3){
        switch (position){
            case 1:
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 自定义箭头朝下
     *
     * @param anchor  相对的控件
     * @param context 上下文
     * @param gravity 针对父布局的位置
     * @param x       距离X轴距离
     * @param y       距离Y轴距离
     */
    public static void showBubbleBottom(View anchor, Context context, int gravity, int x, int y, String text) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_bottom, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .create();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        easyPopupWindow.showAtLocation(anchor, gravity, x, y);
    }

    public static EasyPopupWindow showPopUpBottom(int layout, Context context, View parent, boolean touchOutHide, EasyPopupWindow.ChildClickListener listener) {
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(layout) //设置popupwindow view 里面可以传view, layout
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //设置宽高
                .setBackgroundAlpha(0.7f) //设置背景透明度
                .setAnim(R.style.anim_menu_bottom_bar) //设置动画
                .setAnim(R.style.anim_menu_bottom_bar) //设置动画
                .setOutsideTouchHide(touchOutHide) //点击外面是否关闭
                .setOnChildClickListener(listener) //布局中的事件回调
                .create(); //创建

        easyPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        return easyPopupWindow;
    }

    public static EasyPopupWindow showPopUpTop(int layout, Context context, View parent, boolean touchOutHide, EasyPopupWindow.ChildClickListener listener) {
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(layout)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //                .setBackgroundAlpha(0.7f)
                .setAnim(R.style.anim_menu_top_bar)
                .setOutsideTouchHide(touchOutHide)
                .setOnChildClickListener(listener)
                .create();

        easyPopupWindow.showAsDropDown(parent, 0, 0);

        return easyPopupWindow;
    }

    /**
     * 箭头朝上
     *
     * @param anchor  相对的控件
     * @param context 上下文
     */
    public static void showBubbleUpTeam(View anchor, Context context, String text, int x, int y) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_top_team, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);

        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .setBackgroundAlpha(0.6f)
                .create();
        easyPopupWindow.setAnimationStyle(R.style.popwin_anim_by_businessunit);
        int loc[] = new int[2];
        anchor.getLocationOnScreen(loc);

        easyPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, loc[0]-x-anchor.getWidth()/2, loc[1] + anchor.getHeight()-y);

    }

    public static void showBubbleRight(View anchor, Context context, String text, int x, int y) {
        View bubbleView = View.inflate(context, R.layout.view_bubble_pop_right, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
        tvContent.setText(text);
        EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(context)
                .setView(bubbleView)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchHide(true)
                .setBackgroundAlpha(0.6f)
                .create();
        easyPopupWindow.setAnimationStyle(R.style.popwin_anim_resource_unit);
        int loc[] = new int[2];
        anchor.getLocationOnScreen(loc);
        easyPopupWindow.showAtLocation(anchor, Gravity.LEFT| Gravity.TOP, loc[0]-x-anchor.getWidth(), loc[1] - anchor.getHeight()/2-y);
//        LogUtils.showLog("loc[0]   "+loc[0]+"     anchor.getWidth()     "+anchor.getWidth()+"     loc[1]   "+loc[1]+"     anchor.getHeight()/2    "+anchor.getHeight()/2);

    }
}

package yfwang.androiddemo.activity.BezierDemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yfwang.androiddemo.R;
import yfwang.androiddemo.utils.DensityUtils;


/**
 * Description: 购物车贝塞尔曲线动画特效
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2016/12/3 15:45
 */

public class ShoppingCartBezierActivity extends AppCompatActivity {
    @BindView(R.id.bezier_layout)
    public RelativeLayout mBezierLayout;
    @BindView(R.id.bezier_add)
    public Button mBezierAdd;
    @BindView(R.id.bezier_shopping_cart)
    public ImageView mBezierShoppingCart;
    @BindView(R.id.bezier_food)
    public ImageView mBezierFood;


    private Path mPath = new Path();

    private Point mStartPoint = new Point();
    private Point mEndPoint= new Point();
    private Point mAssistPoint= new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_bert_bezier);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mBezierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView foodView = new ImageView(ShoppingCartBezierActivity.this);
                foodView.setImageResource(R.drawable.food);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtils.dip2px(ShoppingCartBezierActivity.this, 32), DensityUtils.dip2px(ShoppingCartBezierActivity.this, 32));
                mBezierLayout.addView(foodView, params);

                initAnimator(foodView);
            }
        });

    }

    /**
     * 当layout的布局绘制完之后对点的信息进行初始化操作.
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int[] foodLocation = new int[2];
        mBezierFood.getLocationInWindow(foodLocation);

        if(foodLocation.length == 2){
            mStartPoint.set(foodLocation[0], foodLocation[1]);//计算该视图在它所在的widnow的坐标x，y值
        }

        int[] shoppingCartLocation = new int[2];
        mBezierShoppingCart.getLocationInWindow(shoppingCartLocation);

        if(shoppingCartLocation.length == 2){
            mEndPoint.set(shoppingCartLocation[0] + mBezierShoppingCart.getWidth() / 2,
                    shoppingCartLocation[1] - mBezierShoppingCart.getHeight() / 2); //结束点定位在购物车的中间位置
        }

        /**
         * 中间点确定
         */
        int pointX = (mStartPoint.x + mEndPoint.x) / 2;
        int pointY = mStartPoint.y;
        mAssistPoint = new Point(pointX, pointY);
    }

    /**
     * 动画处理
     * @param view
     */
    public void initAnimator(final View view){
        // 重置路径
        mPath.reset();
        // 起点
        mPath.moveTo(mStartPoint.x, mStartPoint.y - DensityUtils.dip2px(ShoppingCartBezierActivity.this, 20));
        // 重要的就是这句
        mPath.quadTo(mAssistPoint.x, mAssistPoint.y , mEndPoint.x, mEndPoint.y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, mPath);
        objectAnimator.setDuration(800);
        objectAnimator.start();

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mBezierLayout.removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

}

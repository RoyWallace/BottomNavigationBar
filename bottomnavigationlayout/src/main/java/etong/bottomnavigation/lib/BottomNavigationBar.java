package etong.bottomnavigation.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by hwt on 2016/3/16.
 */
public class BottomNavigationBar extends LinearLayout {

    private Paint paint;

    private int cx, cy, r;

    private int currentColor;

    private ArrayList<BottomBarTab> tabList = new ArrayList<>();

    private TabListener tabListener;

    private int tabSelectedWidth;
    private int tabDefaultWidth;

    private int imageSelectedTop;
    private int imageDefaultTop;

    private float textDefaultScale;

    private int currentPosition;

    public int animation_duration = 150;

    private float tabWidthSelectedScale = 1.0f;

    public boolean textDefaultVisible = false;

    private boolean colorful = true;

    private int textSelectedColor;

    private int textDefaultColor;

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setGravity(Gravity.CENTER_HORIZONTAL);
        imageSelectedTop = (int) (Resources.getSystem().getDisplayMetrics().scaledDensity * 6);
        imageDefaultTop = (int) (Resources.getSystem().getDisplayMetrics().scaledDensity * 16);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            tabDefaultWidth = (int) (getWidth() / (tabList.size() + tabWidthSelectedScale - 1));
            tabSelectedWidth = (int) (tabDefaultWidth * tabWidthSelectedScale);

            //TODO 此部分代码需要抽取重构
            for (int i = 0; i < getChildCount(); i++) {
                BottomBarTab tab = (BottomBarTab) getChildAt(i);
                LayoutParams params = (LayoutParams) tab.getLayoutParams();
                if (i == currentPosition) {
                    //get the initial selected position and set bottomNavigationBar's background color
                    currentColor = tab.color;
                    params.width = tabSelectedWidth;
                    tab.textView.setVisibility(VISIBLE);
//                    tab.textView.setTextColor(textSelectedColor);
                    tab.imageView.setY(imageSelectedTop);
                    tab.setSelected(true);
                } else {
                    params.width = tabDefaultWidth;
                    tab.imageView.setY(imageDefaultTop);
                    tab.textView.setScaleX(textDefaultScale);
                    tab.textView.setScaleY(textDefaultScale);
//                    tab.textView.setTextColor(textDefaultColor);
                    tab.setSelected(false);
                }
            }
            setBackgroundColor(currentColor);
        }
    }

    public void setSelected(int position) {
        this.currentPosition = position;
        this.currentColor = tabList.get(position).color;
        for (int i = 0; i < tabList.size(); i++) {
            BottomBarTab tab = tabList.get(i);
            if (i == position) {
                tab.setSelected(true);
            } else {
                tab.setSelected(false);
            }
        }
    }

    public void setTabWidthSelectedScale(float tabWidthSelectedScale) {
        this.tabWidthSelectedScale = tabWidthSelectedScale;
        //TODO 需要增加动态修改放大倍数方法
    }

    public void setTextDefaultVisible(boolean visible) {
        this.textDefaultVisible = visible;
        if (textDefaultVisible) {
            imageDefaultTop = (int) (Resources.getSystem().getDisplayMetrics().scaledDensity * 8);
            textDefaultScale = 0.9f;
        } else {
            imageDefaultTop = (int) (Resources.getSystem().getDisplayMetrics().scaledDensity * 16);
            textDefaultScale = 0f;
        }
    }

    public void setColorful(boolean colorful) {
        this.colorful = colorful;
    }

    public void setTextColorResId(int textColorResId) {
        //TODO 设置字体颜色
        ColorStateList stateList = getResources().getColorStateList(textColorResId);
        textSelectedColor = stateList.getColorForState(View.SELECTED_STATE_SET, 0xffffffff);
        textDefaultColor = stateList.getDefaultColor();

    }

    public void ripple(View view, int color) {
        int x = (int) (view.getX() + view.getWidth() / 2);
        int y = (int) (view.getY() + view.getHeight() / 2);
        if (colorful) {
            drawCircle(x, y, getWidth(), color);
        } else {
            drawCircle(x, y, getHeight() / 2, textSelectedColor);
        }
    }

    private void drawCircle(int cx, int cy, int maxR, int color) {
        this.cx = cx;
        this.cy = cy;
        this.currentColor = color;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, maxR);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                r = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackgroundColor(currentColor);
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(currentColor);
        canvas.drawCircle(cx, cy, r, paint);
    }

    public void addTab(int resId, String text) {
        //TODO 待定
    }

    public void addTab(int resId, String text, int color) {
        BottomBarTab tab = new BottomBarTab(getContext());
        tab.setImageResource(resId);
        tab.setText(text);
        tab.color = color;
        //set tab click listener
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handClickEvent((BottomBarTab) v);
            }
        });
        LayoutParams params = (LayoutParams) tab.getLayoutParams();
        if (params == null) {
            params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        addView(tab, params);
        tabList.add(tab);

        postInvalidate();
    }

    public void handClickEvent(BottomBarTab selected) {
        for (int i = 0; i < tabList.size(); i++) {
            final BottomBarTab tab = tabList.get(i);
            if (selected.equals(tab)) {
                tabListener.onSelected(tab, i);
                if (!tab.isSelected()) {
                    tab.setSelected(true);

                    tab.textView.setVisibility(VISIBLE);
                    tab.widthAnimator(tabDefaultWidth, tabSelectedWidth);
                    tab.textScaleAnimator(1);
                    tab.imageTranslationAnimator(imageDefaultTop, imageSelectedTop);
                    tab.animatorStart(animation_duration);

                    ripple(tab, tab.color);
                }
            } else {
                if (tab.isSelected()) {
                    tab.setSelected(false);

                    tab.widthAnimator(tabSelectedWidth, tabDefaultWidth);
                    tab.textScaleAnimator(textDefaultScale);
                    tab.imageTranslationAnimator(imageSelectedTop, imageDefaultTop);
                    tab.animatorStart(animation_duration);

                }
            }
        }
    }

    public void setOnTabListener(TabListener tabListener) {
        this.tabListener = tabListener;
    }

    public interface TabListener {
        void onSelected(BottomBarTab tab, int position);
    }
}

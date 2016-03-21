package etong.bottomnavigation.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
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

    private int tenDp;

    private int sixDp;

    private int selectedWidth;

    private int defaultWidth;

    private int currentPosition;

    private int animation_duration = 250;

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setGravity(Gravity.CENTER_HORIZONTAL);
        sixDp = getResources().getDimensionPixelOffset(R.dimen.sixDp);
        tenDp = getResources().getDimensionPixelOffset(R.dimen.tenDp);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            defaultWidth = (int) (getWidth() / (tabList.size() + 0.5f));
            selectedWidth = (int) (defaultWidth * 1.5f);

            for (int i = 0; i < getChildCount(); i++) {
                BottomBarTab tab = (BottomBarTab) getChildAt(i);
                LayoutParams params = (LayoutParams) tab.getLayoutParams();
                if (i == currentPosition) {
                    params.width = selectedWidth;
                    //get the initial selected position and set bottomNavigationBar's background color
                    currentColor = tab.color;
                    tab.textView.setVisibility(VISIBLE);
                    tab.imageView.setY(sixDp);
                    tab.setSelected(true);
                } else {
                    tab.setSelected(false);
                    params.width = defaultWidth;
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

    public void ripple(View view, int color) {
        int x = (int) (view.getX() + view.getWidth() / 2);
        int y = (int) (view.getY() + view.getHeight() / 2);
        drawCircle(x, y, color);
    }

    private void drawCircle(int cx, int cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.currentColor = color;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, getWidth());
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

    }

    public void addTab(int resId, String text, int color) {
        int index = tabList.size();
        BottomBarTab tab = new BottomBarTab(getContext());
        tab.setImageResource(resId);
        tab.setText(text);
        tab.color = color;
        //设置点击监听
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
    }

    public void handClickEvent(BottomBarTab selected) {
        for (int i = 0; i < tabList.size(); i++) {
            final BottomBarTab tab = tabList.get(i);
            if (selected.equals(tab)) {
                tabListener.onSelected(tab, i);
                tab.setSelected(true);
                if (tab.textView.getVisibility() == GONE) {
                    final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tab.getLayoutParams();
                    ValueAnimator w = ValueAnimator.ofInt(defaultWidth, selectedWidth);
                    w.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            params.width = (int) animation.getAnimatedValue();
                            requestLayout();
                        }
                    });

                    tab.textView.setVisibility(VISIBLE);
                    ObjectAnimator sx = ObjectAnimator.ofFloat(tab.textView, "scaleX", 0, 1);

                    ObjectAnimator sy = ObjectAnimator.ofFloat(tab.textView, "scaleY", 0, 1);

                    ObjectAnimator ty = ObjectAnimator.ofFloat(tab.imageView, "translationY", 0, -tenDp);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(animation_duration);
                    animatorSet.playTogether(w, sx, sy, ty);
                    animatorSet.start();

                    ripple(tab, tab.color);
                }
            } else {
                tab.setSelected(false);
                if (tab.textView.getVisibility() == VISIBLE) {
                    final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tab.getLayoutParams();
                    ValueAnimator w = ValueAnimator.ofInt(selectedWidth, defaultWidth);
                    w.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            params.width = (int) animation.getAnimatedValue();
                            requestLayout();
                        }
                    });

                    ObjectAnimator sx = ObjectAnimator.ofFloat(tab.textView, "scaleX", 1, 0);

                    ObjectAnimator sy = ObjectAnimator.ofFloat(tab.textView, "scaleY", 1, 0);

                    ObjectAnimator ty = ObjectAnimator.ofFloat(tab.imageView, "translationY", -tenDp, 0);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(animation_duration);
                    animatorSet.playTogether(w, sx, sy, ty);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            tab.textView.setVisibility(GONE);
                        }
                    });
                    animatorSet.start();

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

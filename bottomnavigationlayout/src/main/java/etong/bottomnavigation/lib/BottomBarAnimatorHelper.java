package etong.bottomnavigation.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hwt on 2016/3/25.
 */
public class BottomBarAnimatorHelper {

    public static void widthAnimator(final BottomBarTab tab, int startWidth, int endWidth) {
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tab.getLayoutParams();
        ValueAnimator w = ValueAnimator.ofInt(startWidth, endWidth);
        w.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                tab.getParent().requestLayout();
            }
        });
        w.setDuration(150);
        w.start();
    }

    public static void textScaleAnimator(View view, float endScale) {
        ObjectAnimator sx = ObjectAnimator.ofFloat(view, "scaleX", endScale);
        ObjectAnimator sy = ObjectAnimator.ofFloat(view, "scaleY", endScale);
        sx.setDuration(150);
        sy.setDuration(150);
        sx.start();
        sy.start();
    }

    public static void imageTranslationAnimator(View view, float startY, float endY) {
        ObjectAnimator ty = ObjectAnimator.ofFloat(view, "Y", startY, endY);
        ty.setDuration(150);
        ty.start();
    }
}

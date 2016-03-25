package etong.bottomnavigation.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hwt on 2016/3/18.
 */
public class BottomBarTab extends RelativeLayout {

    public ImageView imageView;

    public TextView textView;

    public int color;

    private ArrayList<Animator> animatorList = new ArrayList<>();

    public BottomBarTab(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_bottom_navigation, this, true);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void setImageResource(int imageResource) {
        this.imageView.setImageResource(imageResource);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setSelected(boolean selected) {
        this.imageView.setSelected(selected);
    }

    public boolean isSelected() {
        return imageView.isSelected();
    }

    public void widthAnimator(int startWidth, int endWidth) {
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        ValueAnimator w = ValueAnimator.ofInt(startWidth, endWidth);
        w.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animatorList.add(w);
    }

    public void textScaleAnimator(float endScale) {
        ObjectAnimator sx = ObjectAnimator.ofFloat(textView, "scaleX", endScale);
        ObjectAnimator sy = ObjectAnimator.ofFloat(textView, "scaleY", endScale);
        animatorList.add(sx);
        animatorList.add(sy);
    }

    public void imageTranslationAnimator(float startY, float endY) {
        Log.i("etong", "endY: " + endY);
        ObjectAnimator ty = ObjectAnimator.ofFloat(imageView, "Y", startY, endY);
        animatorList.add(ty);
    }

    public void animatorStart(int duration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animatorList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorList.clear();
            }
        });
        animatorSet.start();
    }

}

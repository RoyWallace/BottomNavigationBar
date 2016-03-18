package etong.bottomnavigation.lib;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by hwt on 2016/3/16.
 */
public class BottomNavigationBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            if (child.getY() == coordinatorLayout.getHeight() - child.getHeight()) {
                child.animate().translationY(child.getHeight()).setDuration(200).start();
            }
        } else {
            if (child.getY() == coordinatorLayout.getHeight()) {
                Log.i("etong", "onNestedPreScroll");
                child.animate().translationY(0).setDuration(250).start();
            }
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

}

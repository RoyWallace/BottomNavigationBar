package etong.bottomnavigation.lib;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hwt on 2016/3/18.
 */
public class BottomBarTab extends RelativeLayout {

    public ImageView imageView;

    public TextView textView;

    public int color;


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

    public void selected(int defaultWidth, int selectedWidth) {


    }

    public void unSelected() {

    }
}

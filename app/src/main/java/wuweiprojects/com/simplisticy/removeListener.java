package wuweiprojects.com.simplisticy;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * Created by cat on 3/31/2017.
 */


public class removeListener implements View.OnClickListener {
    LinearLayout parent;
    View child;
    Context context;

    boolean clicked = false;

    public removeListener(Context context, View view, LinearLayout layout){
        this.context = context;
        child = view;
        parent = layout;
    }

    public boolean isClicked(){
        return clicked;
    }

    @Override
    public void onClick(View view) {
        clicked = true;
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                parent.removeView(child);
            }
        });
        child.startAnimation(fadeOut);

    }
}

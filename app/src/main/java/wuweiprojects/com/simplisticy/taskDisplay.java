package wuweiprojects.com.simplisticy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;


/**
 * Created by cat on 3/29/2017.
 */

public class taskDisplay extends NestedScrollView{
    StringDB database;
    ArrayList<String> tasks;
    ArrayList<Button> taskObjs;
    Context context;
    Window window;
    LinearLayout linearLayout;
    Typeface font;
    LinearLayout.LayoutParams ll;
    int height, width;
    OnClickListener buttonListener;

    public taskDisplay(Context context) {
        super(context);
    }

    public taskDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public taskDisplay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void init(Context c, Window window) {
        final Context tempContext = c; //tempContext to pass into removeListener constructor, needed to be final because removeListener in innerclass
        this.context = c;
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        database = new StringDB(context);
        this.window = window;
        font = Typeface.createFromAsset(context.getAssets(),"fonts/ariallight.ttf");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        ll = new LinearLayout.LayoutParams(width/2, 200); //args = width, height
        ll.gravity = Gravity.CENTER;
        ll.topMargin = 10;
        ll.bottomMargin = 30;

        buttonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                String text = b.getText().toString();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.scrollView),
                        text, Snackbar.LENGTH_SHORT);
                removeListener remover = new removeListener(tempContext, b, linearLayout);
                database.deleteString(text);
                if (remover.isClicked()) database.deleteString(text);
                snackbar.setAction("complete", remover);
                snackbar.show();

            }
        };
        drawTasks();
    }


    private ArrayList<Button> buttonGenerate(ArrayList<String> tasks){
        ArrayList<Button> result = new ArrayList<Button>();
        Animation fadeIn = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fade_in);
        for (String s : tasks){
            Button newButton = new Button(context);
            newButton.setText(s);
            newButton.setLayoutParams(ll);
            newButton.setTypeface(font);
            newButton.setTransformationMethod(null);
            newButton.setOnClickListener(buttonListener);
            newButton.startAnimation(fadeIn);
            result.add(newButton);
        }
        return result;
    }

    //drawTasks: calls arrayGenerate, buttonGenerate(tasks), for loop through buttons and addthem to linearlayout
    public void drawTasks(){
        tasks = database.arrayGenerate();
        taskObjs = buttonGenerate(tasks);

        linearLayout.removeAllViews();
        for (Button b : taskObjs) {
            linearLayout.addView(b);
        }
    }

    public void addTask(String s){
        database.addString(s);
        tasks = database.arrayGenerate();
        Button b = new Button(context);
        b.setText(s);
        b.setTransformationMethod(null);
        b.setLayoutParams(ll);
        b.setTypeface(font);
        b.setOnClickListener(buttonListener);
        Animation fadeIn = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fade_in);
        b.startAnimation(fadeIn);
        linearLayout.addView(b);
    }

}

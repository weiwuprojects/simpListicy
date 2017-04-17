package wuweiprojects.com.simplisticy;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class main extends AppCompatActivity {
    taskDisplay taskArea; //custom class that inherits from NestedScrollView
    Typeface font;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //OnCreate sets up:
    //collapsingtoolbar with title that appears when collapsed
    //welcometext with fading
    //add task button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
                    collapsingToolbar.setTitle("simpListicy");
                    Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    collapsingToolbar.startAnimation(fadeIn);
                }
                else collapsingToolbar.setTitle(" ");

            }
        });

        TextView welcomeText = (TextView) findViewById(R.id.toolbar_title);
        welcomeText.setText("What would you like to do?");
        //"What do you feel like doing?"
        //do what feels natural.
        //it gets done when it gets done.
        //what can get done?
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        welcomeText.startAnimation(fadeIn);

        font = Typeface.createFromAsset(getAssets(),"fonts/ariallight.ttf");
        welcomeText.setTypeface(font);
        Window window = this.getWindow();
        taskArea = (taskDisplay) findViewById(R.id.scrollView);//initializes the scrolling area
        taskArea.init(this, window);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setTitle("What is your task?");
                // Set up the input
                final EditText input = new EditText(main.this);
                input.setHint("Do Laundry");
                //Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskArea.addTask(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }

    public void sendNotification(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle("To Do");
        String notificationBody = new String();
        ArrayList<String> tasks = taskArea.database.arrayGenerate();
        for (String s : tasks){
            notificationBody += "\n\n" + s;
        }
        notification.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationBody));
        //Alert shown when Notification is received
        notification.setTicker("Check tasks");
        //Icon to be set on Notification
        notification.setSmallIcon(android.R.drawable.picture_frame);
        //Creating new Stack Builder
        NotificationManager manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //pop up for settings


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Settings");
            builder.setView(R.layout.settings);



            AlertDialog dialog = builder.show();
            dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
            CheckedTextView enableNotifications = (CheckedTextView) dialog.findViewById(R.id.checkedTextView);
            enableNotifications.setTypeface(font);
            enableNotifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckedTextView c = (CheckedTextView)view;
                    c.toggle();
                    if (c.isChecked()) sendNotification();
                }
            });





//            builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
//                    if (isChecked) {
//                        // If the user checked the item, add it to the selected items
//                        sendNotification();
//                    } else if (!isChecked) {
//                        // Else, if the item is already in the array, remove it
//                    }
//                }
//
//            });


        }

        if (id == R.id.test){
        }


        return super.onOptionsItemSelected(item);
    }
}

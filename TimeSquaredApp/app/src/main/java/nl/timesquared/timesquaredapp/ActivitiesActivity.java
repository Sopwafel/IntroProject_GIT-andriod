package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;

import nl.timesquared.timesquaredapp.Database.EventWriter;
import nl.timesquared.timesquaredapp.Database.KnopDB;
import nl.timesquared.timesquaredapp.Database.Timer;
import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.Entry;
import nl.timesquared.timesquaredapp.Objects.InputEvent;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

public class ActivitiesActivity extends AppCompatActivity {
    ProjectObject project;
    SharedPreferences localprefs;
    InputEvent timer;
    String savedUID;
    Entry active;
    Thread running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        // Can't use a toolbar and actionbar at the same time.
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        // This was really annoying. Intent is the link between an activity and its parent activity.
        // You can't just pass an object to the constructor of an activity because then it becomes null somehow.
        // Finally most things have to be done after setcontentview or you get exceptions.
        final Intent passedIntent = getIntent();
        final ProjectObject project = (ProjectObject) passedIntent.getSerializableExtra("project");
        drawActivities(project);
        savedUID = (String)passedIntent.getSerializableExtra("UID");
        localprefs = this.getPreferences(Context.MODE_PRIVATE);

    }

    public Boolean isLastLink(ActivityLink a){
        String toCompare = a.getProjectID()+a.getActivityID();
        return toCompare.equals(localprefs.getString("lastLink", "unknown"));
    }

    public void setLastLink(ActivityLink a){
        SharedPreferences.Editor editor = localprefs.edit();
        if(a!=null) {
            editor.putString("lastLink", a.getProjectID() + a.getActivityID());
        }
        else{
            editor.putString("lastLink", "placeholder so islastlink returns false");
        }
        editor.apply();
    }

    public void setStartTime(Long time){
        SharedPreferences.Editor editor = localprefs.edit();

            editor.putLong("startTime", time);

        editor.apply();
    }

    public void drawActivities(ProjectObject project){
        LinearLayout activitiesHolder = (LinearLayout)findViewById(R.id.activitiesHolder);
        activitiesHolder.removeAllViews();
        TextView text = new TextView(this);
        text.setText("Tap an activity to start a timer");
        activitiesHolder.addView(text);
        Button button;
        final ProjectObject finalProject = project;
        for(int i = 0;i<project.linkList.size();i++)
        {
            final ActivityLink finalLink = project.linkList.get(i);
            button = new Button(this);
            Log.d("Color of button", Integer.toString(button.getSolidColor()));

            //These colors took me some time to figure out. No nice color type in java
            //TODO make this fetch the activities from the activitylink instead of this ugly cheat, and fix color
            //Log.d("activitycolor", Integer.toString(finalProject.activityList.get(i).color));
            //button.setBackgroundColor(finalProject.activityList.get(i).color);
            button.setText(finalProject.getName());
            //This is really annoying and should be much easier.
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //When a button is clicked, start a timer for its activity
                                              //TODO make it start a timer when that is fixed
                                              //TODO CAPSER dit is de handler voor als we een timer starten. Hier kan je een notificatie starten.
                                              timerChange(finalLink);
                                          }
                                      }
            );
            activitiesHolder.addView(button);
        }

    }

    public void timerChange(ActivityLink link){
        if(isLastLink(link))
        {
            Log.d("timerChange", "isLastLink = true");
            setLastLink(null);
            KnopDB knop = new KnopDB(localprefs.getLong("startTime", 0), savedUID, link, false );

        }
        else{
            Log.d("timerChange", "isLastLink = false");

            setLastLink(link);
            long starttime = System.currentTimeMillis();
            KnopDB knop = new KnopDB(starttime, savedUID, link, true);
            setStartTime(starttime);
        }
    }

    private void StartNewEvent(ActivityLink a){
        timer = new InputEvent(System.currentTimeMillis(),System.currentTimeMillis(), a.getProjectID(), a.getActivityID());
        //TODO put eventreader
        if(isActive(a)) {
            EventWriter.AddEvent(timer);
            Timer timerThread = new Timer(timer);
            running = new Thread(timerThread);
            running.start();
        }
    }

    private Boolean isActive(Entry e){
        return e.equals(active);
    }

    public ActivitiesActivity(){}
}

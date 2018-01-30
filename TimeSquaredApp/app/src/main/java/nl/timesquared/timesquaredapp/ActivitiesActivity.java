package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.timesquared.timesquaredapp.Database.KnopDB;
import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

public class ActivitiesActivity extends AppCompatActivity {
    /**
     * In this thing we can save data
     */
    SharedPreferences localprefs;
    /**
     * UserID for talking to the server
     */
    String savedUID;
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
        localprefs = this.getPreferences(Context.MODE_PRIVATE);

        drawActivities(project);
        savedUID = (String)passedIntent.getSerializableExtra("UID");

    }

    /**
     * Checks if an activity is the currently active timer
     * @param a ActivityLink
     * @return Boolean
     */
    public Boolean isLastTimer(ActivityLink a){
        String toCompare = a.getProjectID()+a.getActivityID();
        return toCompare.equals(localprefs.getString("lastLink", "unknown"));
    }

    /**
     * Saves an ActivityLink as the active timer
     * @param a ActivityLink
     */
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

    /**
     * Saves the startTime of the current timer so we can UPDATE it
     * @param time startTime
     */
    public void setStartTime(Long time){
        SharedPreferences.Editor editor = localprefs.edit();

            editor.putLong("startTime", time);

        editor.apply();
    }

    /**
     * Draws buttons for all activities in a project
     * @param project Project
     */
    public void drawActivities(final ProjectObject project){
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

            //These colors took me some time to figure out. No nice color type in java
            button.setText(finalProject.getActivity(finalLink).getName());
            //This is really annoying and should be much easier.
            if(isLastTimer(finalLink))
                //Exception pls go away
                button.setBackgroundColor(Color.RED);
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //When a button is clicked, start a timer for its activity
                                              //TODO make it start a timer when that is fixed
                                              //TODO CAPSER dit is de handler voor als we een timer starten. Hier kan je een notificatie starten.
                                              timerChange(finalLink);
                                              drawActivities(project);
                                          }
                                      }
            );
            activitiesHolder.addView(button);
        }

    }

    /**
     * Starts or stops a timer. Does all checks.
     * @param link
     */
    public void timerChange(ActivityLink link){
        if(isLastTimer(link))
        {
            Log.d("timerChange", "isLastTimer = true");
            setLastLink(null);
            KnopDB knop = new KnopDB(localprefs.getLong("startTime", 0), savedUID, link, false );

        }
        else{
            Log.d("timerChange", "isLastTimer = false");

            setLastLink(link);
            final long starttime = System.currentTimeMillis();
            setStartTime(starttime);

            KnopDB knop = new KnopDB(localprefs.getLong("startTime", 0), savedUID, link, true);
        }
    }
    public ActivitiesActivity(){}
}

package nl.timesquared.timesquaredapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import nl.timesquared.timesquaredapp.Database.KnopDB;
import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

public class ActivitiesActivity extends AppCompatActivity {
    Notification notification;
    /**
     * This manages notifications
     */
    NotificationManager notificationManager = null;
    /**
     * In this thing we can save global app data
     */
    SharedPreferences localprefs;
    /**
     * UserID for talking to the server
     */
    String savedUID;
    ProjectObject thisProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        // This was really annoying. Intent is the link between an activity and its parent activity.
        // You can't just pass an object to the constructor of an activity because then it becomes null somehow.
        // Finally most things have to be done after setContentView or you get exceptions.
        final Intent passedIntent = getIntent();
        final ProjectObject project = (ProjectObject) passedIntent.getSerializableExtra("project");
        localprefs = PreferenceManager.getDefaultSharedPreferences(this);
        thisProject = project;
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
     * Saves the projectID of the active timer so we can color the correct button red in MainActivity
     * @param p project
     */
    public void setLastProject(ProjectObject p){
        SharedPreferences.Editor editor = localprefs.edit();
        if(p!=null) {
            editor.putString("lastProject", p.getID());
        }
        else{
            editor.putString("lastProject", "placeholder so isLastProject returns false");
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
                // Exception pls go away
                button.setBackgroundColor(Color.RED);
                // Thank you
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //When a button is clicked, start a timer for its activity
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
            setLastLink(null);
            setLastProject(null);
            long starttime = localprefs.getLong("startTime", 0);
            long totaltime = System.currentTimeMillis() - starttime;
            // Making the button does all the important things.
            KnopDB knop = new KnopDB(starttime, savedUID, link, false );
            // TODO We need to get this from a bundle in sharedprefs so we don't lose it out of scope. Should make null check obsolete.
            if(notificationManager!=(null))
                notificationManager.cancelAll();
            // This bakes a toast that displays how long the timer had been turned on.
            Toast toast = Toast.makeText(getApplicationContext(),"Time spent: "+ String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(totaltime),
                    TimeUnit.MILLISECONDS.toSeconds(totaltime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totaltime))
            ), Toast.LENGTH_SHORT );
            toast.show();
        }
        else{
            String activityName = "";
            for(ActivityObject activityObject : thisProject.activityList) {
                activityName = activityObject.getName();
                if (activityObject.getID().equals(link.getActivityID()))
                    break;
            }
            // These are necessary for button coloring
            setLastLink(link);
            setLastProject(thisProject);

            // These put the timer in the server.
            final long starttime = System.currentTimeMillis();
            setStartTime(starttime);
            // This is ugly but it works so why make it pretty. (Server functionality is in the constructor)
            KnopDB knop = new KnopDB(localprefs.getLong("startTime", 0), savedUID, link, true);


            // This makes a notification while the timer is running
            // TODO put this in a notificationBundle in SharedPreferences so it doesn't get lost out of scope when closing the app BIG IMPORTANT IMPROVEMENT
            Notification.Builder builder = new Notification.Builder(getApplicationContext());
            builder.setContentTitle("TimeSquared");
            builder.setContentText("Currently running timer: "+activityName);
            builder.setOngoing(true);
            builder.setSmallIcon(R.drawable.ic_stat_name);
            // This is the click handler
            // I'd love to open ActivitiesActivity but that requires a ProjectObject supplied by MainActivity. So we're going with MainActivity.
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent notificationClick = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(notificationClick);
            // And this finalizes/displays it.
            notification = builder.build();
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(01, notification);

        }
    }
    public ActivitiesActivity(){}
}

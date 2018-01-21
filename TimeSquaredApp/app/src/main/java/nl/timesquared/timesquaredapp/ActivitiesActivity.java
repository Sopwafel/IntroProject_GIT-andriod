package nl.timesquared.timesquaredapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

public class ActivitiesActivity extends AppCompatActivity {
    ProjectObject project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        //Can't use a toolbar and actionbar at the same time.
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //This was really annoying. Intent is the link between an activity and its parent activity.
        //You can't just pass an object to the constructor of an activity because then it becomes null somehow.
        //Finally most things have to be done after setcontentview or you get exceptions.
        final Intent passedIntent = getIntent();
        final ProjectObject project = (ProjectObject) passedIntent.getSerializableExtra("project");
        drawActivities(project);
    }

    public void drawActivities(ProjectObject project){
        LinearLayout activitiesHolder = (LinearLayout)findViewById(R.id.activitiesHolder);
        activitiesHolder.removeAllViews();
        Button button;
        final ProjectObject finalProject = project;
        for(int i = 0;i<project.activityList.size();i++)
        {
            final int j=i;
            button = new Button(this);
            button.setText(finalProject.activityList.get(i).getName());
            //This is really annoying and should be much easier.
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //When a button is clicked, open an activitiesActivity with the corresponding project
                                              startTimer(finalProject.activityList.get(j));
                                          }
                                      }
            );
            activitiesHolder.addView(button);
        }
    }
    public void startTimer(ActivityObject activity){

    }


    /**
     * This is never used but it won't work without because of some stupid check that looks if there is a standard constructor.
     */
    public ActivitiesActivity(){}
}

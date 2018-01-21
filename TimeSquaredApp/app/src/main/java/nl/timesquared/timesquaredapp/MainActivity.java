package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import nl.timesquared.timesquaredapp.Database.getProjects;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    List<ProjectObject> testList = new ArrayList<ProjectObject>();
    String savedUID;
    SharedPreferences localPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        putToolbar();

        // This makes sure the UserID is accessible
        localPrefs = this.getPreferences(Context.MODE_PRIVATE);
        savedUID = localPrefs.getString("USER_ID", "unknown");
        setUserID("d2c1b357-8041-4aef-9b41-da49db7a2aa6");
    }
//    protected void putToolbar()
//    {
//        // This makes the toolbar not empty
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//    }
    public void setUserID(String ID)
    {
        SharedPreferences.Editor editor = localPrefs.edit();
        editor.putString("USER_ID", ID);
        editor.apply();
        savedUID = localPrefs.getString("USER_ID", "unknown");
    }

    /**
     * Testmethod that is bound to a button.
     * @param a
     */
    public void testclick(android.view.View a)
    {
        ProjectObject test1 = new  ProjectObject(UUID.randomUUID(), "test",1,1);
        test1.activityList.add(new ActivityObject(UUID.randomUUID(), "UserID: "+savedUID, 1, 1));
        test1.activityList.add(new ActivityObject(UUID.randomUUID(), "Dildo's gooien", 1, 1));
        test1.activityList.add(new ActivityObject(UUID.randomUUID(), "Dildo's vreten", 1, 1));


        testList.add(test1);
        testList.add(new ProjectObject(UUID.randomUUID(), "dildoi2",1,2));
        testList.add(new ProjectObject(UUID.randomUUID(), "dildo4i",1,3));
        // TODO: fetch projects from database
        // The line below is how you execute an AsyncTask. But I don't know yet how to then access it here as the results are in a different thread
         new getProjects().execute();

        //new JavaQL().execute();
    }

    /**
     * Makes an intent and starts it
     */
    public void startActivityIntent(ProjectObject project)
    {
        Intent tostart = new Intent(this, new ActivitiesActivity().getClass());
        tostart.putExtra("project", project);
        startActivity(tostart);
    }

    /**
     * This draws the buttons for all projects in the projectlist
     */
    public void drawProjects()
    {
        LinearLayout projectHolder = (LinearLayout)findViewById(R.id.projectHolder);
        projectHolder.removeAllViews();
        Button button;
        if(!savedUID.equals("unknown")) {
            Log.d("userid: ", savedUID);
            for (int i = 0; i < testList.size(); i++) {
                final int j = i;
                button = new Button(this);
                button.setText(testList.get(i).getName());
                //This is really annoying and should be much easier.
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  //When a button is clicked, open an activitiesActivity with the corresponding project
                                                  final ProjectObject project = testList.get(j);
                                                  startActivityIntent(project);
                                              }
                                          }
                );
                projectHolder.addView(button);
            }
        }
        else
        {
            //All this block does is ask the user to enter their userID
            button = new Button(this);
            button.setText("Enter useID");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    askUserID();
                }
            });
            projectHolder.addView(button);
        }
//        putToolbar();

    }

    private void askUserID(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("UserID");
        // Set up the input
        final EditText input = new EditText(MainActivity.this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setUserID(input.getText().toString());
                drawProjects();
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

}

package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import nl.timesquared.timesquaredapp.Database.getProjects;
import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
    Uitleg for Casper!
    Things you can do:
    -   Figure out how to make a persistent notification. In Android you often have to declare these things in files like android.manifest or some res file.
        For example I had to make all menuItems in the res/menu folder. I will write the method you need to attach to it, as soon as you know where I have to put it.
        Seems a bit odd to put it in an activity and have no idea where else.
    -   Get the buttons in drawProjects and drawActivities to be the correct color. Right now they have a standard color. Both object types already have their colors inside them when
        we call drawProjects or drawActivities. They're saved as integers though, and when I tried button.setBackgroundColor(project.getColor())); they became transparent. Good luck!
     */

    /**
     * List of Projects fetched from the server. Each project contains its  Activities and ActivityLinks
     */
    List<ProjectObject> testList = new ArrayList<ProjectObject>();
    /**
     * UserID for getting things from the server
     */
    String savedUID;
    /**
     * You can save things like settings in here
     */
    SharedPreferences localPrefs;
    @Override
    /**
     * Is called right after construction. You can't give variables to constructors of activities, you do that with Intents
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        putToolbar();

        // This makes sure the UserID is accessible
        localPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // setUserID("d2c1b357-8041-4aef-9b41-da49db7a2aa6");
        setUserID("398fb7b4-7a2e-47f9-9aa1-ae5f9939f1e4");
        savedUID = localPrefs.getString("USER_ID", "unknown");
        getProjects();
        drawProjects();
    }

    @Override
    /**
     * Adds menuItems
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginbutton, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    /**
     * Adds handlers to menuItems
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.loginbutton) {
            askUserID();
        }
        return super.onOptionsItemSelected(item);
    }
//    protected void putToolbar()
//    {
//        // This makes the toolbar not empty
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//    }

    /**
     * Saves a UserID in the app settings
     * @param ID userID
     */
    public void setUserID(String ID)
    {
        SharedPreferences.Editor editor = localPrefs.edit();
        editor.putString("USER_ID", ID);
        editor.apply();
        savedUID = localPrefs.getString("USER_ID", "unknown");
    }

    /**
     * Gets projects from the server
     */
    private void getProjects(){
        new getProjects().execute(savedUID);
        try{
            testList = new getProjects().execute(new String[]{savedUID}).get();

        }
        catch (Exception e)
        {
            Log.d("getProjects", "error emssage in MainActivity.getProjects: "+e.getMessage());
        }
    }

    /**
     * Adds placeholder projects for the demonstration, as we don't have enough time to figure out how to get the server to work
     * Right now the server returns empty resultSets :(
     * And there is small time left
     */
    private void addTestProjects()
    {
        ProjectObject test1, test2, test3;
        test1= new  ProjectObject("1", "IntroProject",1);
        test1.activityList.add(new ActivityObject("1", "Meeting", android.R.color.white));
        test1.linkList.add(new ActivityLink("1", "1", true));
        test1.activityList.add(new ActivityObject("2", "Android app", android.R.color.holo_red_dark));
        test1.linkList.add(new ActivityLink("1", "2", true));
        test1.activityList.add(new ActivityObject("3", "AnalyticsForm", android.R.color.black));
        test1.linkList.add(new ActivityLink("1", "3", true));


        test2 = new ProjectObject("2", "Sport",1);
        test2.activityList.add(new ActivityObject("1", "Kickboksen", 1));
        test2.linkList.add(new ActivityLink("2", "1", true));
        test2.activityList.add(new ActivityObject("2", "Fitness", 1));
        test2.linkList.add(new ActivityLink("2", "2", true));
        test2.activityList.add(new ActivityObject("3", "Boulderen", 1));
        test2.linkList.add(new ActivityLink("2", "3", true));


        test3 = new ProjectObject("", "Dansen",1);

        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        drawProjects();
    }

    /**
     * Opens a project's activity page
     */
    public void startActivityIntent(ProjectObject project)
    {
        // So this intent is like a link between activities. You have to set child activities somewhere else to get a back button
        Intent toStart = new Intent(this, new ActivitiesActivity().getClass());
        // We can retrieve an extra from the intent in the child activity. That is how you pass data around
        toStart.putExtra("project", project);
        toStart.putExtra("UID", savedUID);

        startActivity(toStart);
    }

    /**
     * This draws the buttons for all projects in the projectlist
     */
    public void drawProjects()
    {
        // Getting UI element
        LinearLayout projectHolder = (LinearLayout)findViewById(R.id.projectHolder);
        projectHolder.removeAllViews();

        TextView text = new TextView(this);
        text.setText("Select a project");
        projectHolder.addView(text);

        Button button;
        // Only draw the projects if we know an UserID
        if(!savedUID.equals("unknown")) {
            Log.d("userid: ", savedUID);
            for (int i = 0; i < testList.size(); i++) {
                final int j = i;
                final ProjectObject project = testList.get(j);
                button = new Button(this);

                if(isLastProject(project))
                    button.setBackgroundColor(Color.RED);
                button.setText(testList.get(i).getName());
                //This is really annoying and should be much easier.
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  //When a button is clicked, open an activitiesActivity with the corresponding project
                                                  startActivityIntent(project);
                                              }
                                          }
                );
                projectHolder.addView(button);
            }
        }
        // If we don't know UserID, ask for it
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

    /**
     * Checks if an Project is the currently active timer
     * @param p ProjectObject
     * @return Boolean
     */
    public Boolean isLastProject(ProjectObject p){
        String toCompare =p.getID();
        Log.d("isLastProject", "Project in button: "+ p.getID()+" getString: "+localPrefs.getString("lastProject", "unknown"));
        return toCompare.equals(localPrefs.getString("lastProject", "unknown"));
    }

    /**
     * Shows a dialog that asks for userID and saves it.
     */
    private void askUserID(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("UserID");
        // Set up the input
        builder.setMessage("Please enter the ID you use on the desktop app:");
        final EditText input = new EditText(MainActivity.this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Confirm ID", new DialogInterface.OnClickListener() {
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

    /**
     * Helper method that casts all items in a list to a different type
     * @param srcList List
     * @param clas Class
     * @param <T> Type
     * @return Converted list
     */
    public <T>List<T> castCollection(List srcList, Class<T> clas){
        List<T> list =new ArrayList<T>();
        for (Object obj : srcList) {
            if(obj!=null && clas.isAssignableFrom(obj.getClass()))
                list.add(clas.cast(obj));
        }
        return list;
    }
}

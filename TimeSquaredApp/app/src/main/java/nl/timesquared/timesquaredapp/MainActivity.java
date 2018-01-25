package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import nl.timesquared.timesquaredapp.Database.getProjects;
import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
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
       // setUserID("d2c1b357-8041-4aef-9b41-da49db7a2aa6");
        savedUID = localPrefs.getString("USER_ID", "unknown");
        addProjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginbutton, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
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
    public void setUserID(String ID)
    {
        SharedPreferences.Editor editor = localPrefs.edit();
        editor.putString("USER_ID", ID);
        editor.apply();
        savedUID = localPrefs.getString("USER_ID", "unknown");
    }

    private void addProjects(){
        new getProjects().execute(savedUID);
        try{
            List<Object> tempList = new getProjects().execute(new String[]{savedUID}).get();
            testList=castCollection(tempList, ProjectObject.class);
        }
        catch (Exception e)
        {
            Log.d("addProjects", "error emssage in MainActivity.addProjects: "+e.getMessage());
        }
        Log.d("testlist[0]", testList.get(0).getName());
        Log.d("testlist[1]", testList.get(1).getName());

        drawProjects();
    }

    /**
     * Adds placeholder projects for the demonstration, as we don't have enough time to figure out how to get the server to work
     * Right now the server returns empty resultSets :(
     * And there is small time left
     */
    public void addTestProjects()
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
        // TODO: fetch projects from database
        // The line below is how you execute an AsyncTask. But I don't know yet how to then access it here as the results are in a different thread. Also server returns empty results so it's useless for now.

        //new JavaQL().execute();
    }

    /**
     * Makes an intent and starts it
     */
    public void startActivityIntent(ProjectObject project)
    {
        Intent toStart = new Intent(this, new ActivitiesActivity().getClass());
        toStart.putExtra("project", project);
        startActivity(toStart);
    }

    /**
     * This draws the buttons for all projects in the projectlist
     */
    public void drawProjects()
    {
        LinearLayout projectHolder = (LinearLayout)findViewById(R.id.projectHolder);
        projectHolder.removeAllViews();
        TextView text = new TextView(this);
        text.setText("Select a project");
        projectHolder.addView(text);
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

package nl.timesquared.timesquaredapp;

import android.content.Context;
import android.graphics.ColorSpace;
import android.util.Log;
import nl.timesquared.timesquaredapp.Database.JavaQL;
import nl.timesquared.timesquaredapp.Objects.NamedEntry;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
List<ProjectObject> testList = new ArrayList<ProjectObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void testclick(android.view.View a)
    {
        testList.add(new ProjectObject(UUID.randomUUID(), "dildoi",1,1));
        testList.add(new ProjectObject(UUID.randomUUID(), "dildoi2",1,2));
        testList.add(new ProjectObject(UUID.randomUUID(), "dildo4i",1,3));
        new JavaQL().execute();
        drawProjects();
        //new JavaQL().execute();
    }
    public void drawProjects(){
        LinearLayout projectHolder = (LinearLayout)findViewById(R.id.projectHolder);
        Button button;
        for(int i = 0;i<testList.size();i++)
        {
            button = new Button(this);
            button.setText(testList.get(i).getName());
            projectHolder.addView(button);
        }
    }
    public Button makeButton(Context context){
        Button button = new Button(context);
        button.setText(name);
        return button;
    }
}

package nl.timesquared.timesquaredapp;

import android.util.Log;
import nl.timesquared.timesquaredapp.Database.JavaQL;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void testclick(android.view.View a)
    {
        Log.d("click", "We push button");
        new JavaQL().execute();
    }
}

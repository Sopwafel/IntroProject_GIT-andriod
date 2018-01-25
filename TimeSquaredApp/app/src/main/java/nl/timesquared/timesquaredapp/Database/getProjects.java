package nl.timesquared.timesquaredapp.Database;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 20-1-2018.
 */

public class getProjects extends JavaQL {
    ArrayList<Object> returnlist = new ArrayList<>();
    protected List<Object> doInBackground(String... params)
    {
        String[] wat = { "Project_ID", "Project_Naam", "Project_Kleur", "Volgorde_Nummer", "Project_Actief", "UserID" };
        String UID = params[0];
        Log.d("uid", UID);
        ArrayList<Object> output = new ArrayList<>();
        //ServerConnection();
        output = Select(wat, "Projects",new String[]{"UserID"}, new String[]{UID}, "ProjectObject");

        return output;
    }

}

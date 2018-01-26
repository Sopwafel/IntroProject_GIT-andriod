package nl.timesquared.timesquaredapp.Database;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 20-1-2018.
 */

public class getProjects extends JavaQL {
    ArrayList<Object> returnlist = new ArrayList<>();
    String UID;
    protected List<Object> doInBackground(String... params)
    {
        String[] wat = { "Project_ID", "Project_Naam", "Project_Kleur", "Volgorde_Nummer", "Project_Actief", "UserID" };
        UID = params[0];
        ArrayList<Object> output;
        //ServerConnection();
        output = Select(wat, "Projects", new String[]{"UserID"}, new String[]{UID}, "ProjectObject");

        List<ActivityLink> activityLinks = getActivityLinks();
        List<Activity> activities = getActivities();
        return output;
    }

    public  List<ActivityLink> getActivityLinks()
    {
        List<ActivityLink> links = linkSelect(new String[] { "UserID" }, new String[] { UID});
        return links;
    }

    public List<Activity> getActivities(){
        List<Activity> activities = activitySelect(new String[] { "UserID" }, new String[] { UID});
        return activities;
    }

    public List<Activity> activitySelect(String[] kolommen_voorwaarden, String[] waarden_voorwaarden)
    {
        String[] wat = { "Activiteit_ID", "Activiteit", "kleur", "UserID" };
        Log.d("Select", "We should be fetching all activities now");
        List<Object> objecten = Select(wat, "Activiteiten", kolommen_voorwaarden, waarden_voorwaarden, "Activity");
        List<Activity> output = castCollection(objecten, Activity.class);
        return output;

    }

    /**
     * Calls Select with the parameters to fetch all ActivityLinks from the userID.
     * Returns a list with all ActivityLinks of an User from the server
     * @param kolommen_voorwaarden
     * @param waarden_voorwaarden
     * @return
     */
    public List<ActivityLink> linkSelect(String[] kolommen_voorwaarden, String[] waarden_voorwaarden)
    {
        String[] wat = { "Project_ID", "Activiteit_ID", "Actief", "UserID"};
        Log.d("Select", "We should be fetching all activityLinks now");
        List<Object> objecten = Select(wat, "Activity_Link", kolommen_voorwaarden, waarden_voorwaarden, "Activity_link");
        List<ActivityLink> output = castCollection(objecten, ActivityLink.class);
        return output;

    }
}

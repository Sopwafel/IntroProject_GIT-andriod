package nl.timesquared.timesquaredapp.Database;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ActivityObject;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 20-1-2018.
 */

public class getProjects extends JavaQL {
    ArrayList<Object> returnlist = new ArrayList<>();
    String UID;
    protected List<ProjectObject> doInBackground(String... params)
    {
        String[] wat = { "Project_ID", "Project_Naam", "Project_Kleur", "Volgorde_Nummer", "Project_Actief", "UserID" };
        UID = params[0];
        ArrayList<Object> output;
        //ServerConnection();
        output = Select(wat, "Projects", new String[]{"UserID"}, new String[]{UID}, "ProjectObject");

        List<ProjectObject> projectList = castCollection(output, ProjectObject.class);

        List<ActivityLink> activityLinks = getActivityLinks();
        List<ActivityObject> activities = getActivities();

        putLinksInProjects(projectList, activityLinks);
        putActivitiesInProjects(projectList, activities);
        return projectList;
    }
    public List<ProjectObject> putLinksInProjects(List<ProjectObject> projectlist, List<ActivityLink> linkList)
    {
        for(ActivityLink link : linkList){
            for(ProjectObject project : projectlist)
                if(link.getProjectID() == project.getID())
                    project.linkList.add((link));
        }
        return projectlist;
    }
    public List<ProjectObject> putActivitiesInProjects(List<ProjectObject> projectlist, List<ActivityObject> activityList)
    {
        for(ActivityObject activity : activityList){
            for(ProjectObject project : projectlist)
                for(ActivityLink link : project.linkList)
                    if(link.getActivityID()==activity.getID())
                        project.activityList.add(activity);
        }
        return projectlist;
    }

    public  List<ActivityLink> getActivityLinks()
    {
        List<ActivityLink> links = linkSelect(new String[] { "UserID" }, new String[] { UID});
        return links;
    }

    public List<ActivityObject> getActivities(){
        List<ActivityObject> activities = activitySelect(new String[] { "UserID" }, new String[] { UID});
        return activities;
    }

    public List<ActivityObject> activitySelect(String[] kolommen_voorwaarden, String[] waarden_voorwaarden)
    {
        String[] wat = { "Activiteit_ID", "Activiteit", "kleur", "UserID" };
        Log.d("Select", "We should be fetching all activities now");
        List<Object> objecten = Select(wat, "Activiteiten", kolommen_voorwaarden, waarden_voorwaarden, "Activity");
        List<ActivityObject> output = castCollection(objecten, ActivityObject.class);
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

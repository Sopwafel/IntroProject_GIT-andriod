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
    /**
     * UserID for talking to the server
     */
    String UID;

    /**
     * Call like this: List<ProjectObject> list = new getProjects.execute();
     * Gets data from the server in the background
     * @param params String[0] should contain UserID
     * @return List<ProjectObject>
     */
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

        projectList = putLinksInProjects(projectList, activityLinks);
        projectList = putActivitiesInProjects(projectList, activities);
        return projectList;
    }

    /**
     * Puts links in a list in the correct projects.
     * @param projectlist projects
     * @param linkList links
     * @return reconciled projects
     */
    public List<ProjectObject> putLinksInProjects(List<ProjectObject> projectlist, List<ActivityLink> linkList)
    {
        for(ActivityLink link : linkList){
            for(ProjectObject project : projectlist)
                project.putActivityLink(link);
        }
        return projectlist;
    }

    /**
     * Puts activities in the correct projects. Only works after links have been added
     * @param projectlist projects
     * @param activityList links
     * @return completed projects
     */
    public List<ProjectObject> putActivitiesInProjects(List<ProjectObject> projectlist, List<ActivityObject> activityList)
    {
        for(ActivityObject activity : activityList){
            for(ProjectObject project : projectlist)
                project.putActivity(activity);
        }
        return projectlist;
    }

    /**
     * Gets all the users links from the server
     * @return
     */
    public  List<ActivityLink> getActivityLinks()
    {
        List<ActivityLink> links = linkSelect(new String[] { "UserID" }, new String[] { UID});
        return links;
    }

    /**
     * Gets all the users activities from the server
     * @return Activities
     */
    public List<ActivityObject> getActivities(){
        List<ActivityObject> activities = activitySelect(new String[] { "UserID" }, new String[] { UID});
        return activities;
    }

    /**
     * Calls Select with the parameters to fetch all Activities from the userID.
     * Returns a list with all Activities of an User from the server.
     * getActivities() should be called instead of this.
     * @param kolommen_voorwaarden
     * @param waarden_voorwaarden
     * @return Activities
     */
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
     * Returns a list with all ActivityLinks of an User from the server.
     * getActivityLinks() should be called instead of this.
     * @param kolommen_voorwaarden
     * @param waarden_voorwaarden
     * @return ActivityLinks
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

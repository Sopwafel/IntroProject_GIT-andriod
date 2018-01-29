package nl.timesquared.timesquaredapp.Objects;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.timesquared.timesquaredapp.ActivitiesActivity;

/**
 * Created by Sopwafel on 18-1-2018.
 */

public class ProjectObject extends NamedEntry{
    private Boolean Active;
    public Boolean getActive(){
        return Active;
    }
    public List<ActivityObject> activityList = new ArrayList<ActivityObject>();
    public List<ActivityLink> linkList = new ArrayList<ActivityLink>();
    public ProjectObject(String ID, String name, int color)
    {
        super(ID, name, color);
        Active = true;
    }

    /**
     * Finds the ActivityLink between the project and an ActivityObject
     * @param activityToFindLinkOf
     * @return ActivityLink
     */
    public ActivityLink getActivityLink(ActivityObject activityToFindLinkOf)
    {
        ActivityLink output;
        for(int i = 0;i<linkList.size();i++)
        {
            output = linkList.get(i);
            if(activityToFindLinkOf.getID().equals(output.getActivityID()))
                return output;
        }
        return null;
    }

    public ActivityObject getActivity(ActivityLink linkToFindActivityOf){
        ActivityObject output;
        for(int i = 0;i<activityList.size();i++)
        {
            output = activityList.get(i);
            if(linkToFindActivityOf.getActivityID().equals(output.getID()))
                return output;
        }
        return null;
    }

    /**
     * Check if a link belongs to this project, add it to the linklist if it does
     * @param aLink link to be checked
     */
    public void putActivityLink(ActivityLink aLink){
        if(aLink.getProjectID().equals(getID()))
            linkList.add(aLink);
        else
            Log.d("putActivity", "Link project ID: " + aLink.getProjectID()+", Project project ID: " +getID());
    }

    /**
     * Checks if an activity belongs to this project, add it to the activitylist if it does. Only works after filling linklist
     * @param activityObject activity to be checked
     */
    public void putActivity(ActivityObject activityObject){
        for(ActivityLink link : linkList)
            if(link.getActivityID().equals(activityObject.getID()))
                activityList.add(activityObject);
    }
}

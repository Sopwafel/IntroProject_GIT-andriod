package nl.timesquared.timesquaredapp.Objects;

import android.content.Context;
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
}

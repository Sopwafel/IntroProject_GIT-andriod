package nl.timesquared.timesquaredapp.Objects;

import java.util.UUID;

/**
 * Created by V.A. van den Broek on 19/01/2018.
 */

public class ActivityLink extends Entry {

    private String ProjectID;
    public String getProjectID()
    {
        return ProjectID;
    }
    private String ActivityID;
    public String getActivityID()
    {
        return ActivityID;
    }
    public Boolean Actief;
    public ActivityLink(String projectID, String activityID, Boolean actief)
    {
        ProjectID=projectID;
        ActivityID=activityID;
        Actief=actief;
    }

}

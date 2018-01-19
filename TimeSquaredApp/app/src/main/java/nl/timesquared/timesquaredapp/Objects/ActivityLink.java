package nl.timesquared.timesquaredapp.Objects;

import java.util.UUID;

/**
 * Created by V.A. van den Broek on 19/01/2018.
 */

public class ActivityLink extends Entry {
    private UUID ProjectID;
    public UUID getProjectID()
    {
        return ProjectID;
    }
    private UUID ActivityID;
    public UUID getActivityID()
    {
        return ActivityID;
    }
    public Boolean Actief;
    public ActivityLink(UUID projectID, UUID activityID, Boolean actief)
    {
        ProjectID=projectID;
        ActivityID=activityID;
        Actief=actief;
    }
}

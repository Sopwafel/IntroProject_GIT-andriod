package nl.timesquared.timesquaredapp.Objects;

import java.sql.Date;

/**
 * Created by Sopwafel on 22-1-2018.
 * Apparently we don't need this
 */

public class InputEvent extends TimedEvent {
    String ProjectID;
    public String getProjectID(){
        return ProjectID;
    }
    String ActivityID;
    public String getActivityID(){
        return ActivityID;
    }
    public InputEvent(long start, long end, String projectID, String activityID){
        super(start,end);
        this.ProjectID =projectID;
        this.ActivityID=activityID;
    }
}

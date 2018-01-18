package nl.timesquared.timesquaredapp.Objects;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sopwafel on 18-1-2018.
 */

public class ProjectObject extends NamedEntry{
    private Boolean Active;
    public Boolean getActive(){
        return Active;
    }
    public List<ActivityObject> activityList;
    public ProjectObject(UUID ID, String name, int color, int order)
    {
        super(ID, name, color, order);
        Active = true;
    }
}

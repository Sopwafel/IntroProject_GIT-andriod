package nl.timesquared.timesquaredapp.Objects;

import android.content.Context;
import android.view.View;
import android.widget.Button;

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
    public List<ActivityObject> activityList;
    public ProjectObject(UUID ID, String name, int color, int order)
    {
        super(ID, name, color, order);
        Active = true;
    }

    @Override
    public Button makeButton(Context context) {
        Button button = super.makeButton(context);
        button.setOnClickListener(onClick());
        });
    }
    public void onClick(View view) {
        ActivitiesActivity activityView = new ActivitiesActivity(this);
    }
}

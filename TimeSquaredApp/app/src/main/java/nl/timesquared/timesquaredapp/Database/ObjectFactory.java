package nl.timesquared.timesquaredapp.Database;

import java.util.ArrayList;
import java.util.UUID;

import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 20-1-2018.
 */

public class ObjectFactory {
    public Object Makeojbects(ArrayList<String> DB_entry, String type){
        switch (type)
        {
            case "ProjectObject":
                return Project_Object(DB_entry);
//            case "Activiteit_object":
//                return Activiteit_Object(DB_entry);
//            case "Activity_link":
//                return link_object(DB_entry);
            default:
                break;
        }
        return null;
    }

    public static Object Project_Object(ArrayList<String> DB_entry){
        ProjectObject projectje = new ProjectObject(DB_entry.get(0), DB_entry.get(1), Integer.parseInt(DB_entry.get(2)));
        return projectje;
    }
}

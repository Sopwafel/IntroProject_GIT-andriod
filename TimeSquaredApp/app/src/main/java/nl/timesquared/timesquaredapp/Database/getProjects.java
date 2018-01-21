package nl.timesquared.timesquaredapp.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sopwafel on 20-1-2018.
 */

public class getProjects extends JavaQL {
    protected List<Object> doInBackground(String... params)
    {
        ArrayList<Object> output = new ArrayList<>();
        ServerConnection();
        return output;
    }
}

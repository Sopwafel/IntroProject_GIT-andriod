package nl.timesquared.timesquaredapp.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 23-1-2018.
 */

public  class KnopDB {
    long begintijd_opslag;
    String UID;
    static String[] kolommen = { "Begintijd", "Eindtijd", "Project_ID", "Activiteit_ID", "Synced" };
    static String[] kolommen_online = { "Begintijd", "Eindtijd", "Project_ID", "Activiteit_ID", "UserID" };

    /**
     * This is so ugly but Java doesn't allow static base classes.
     * @param starttime
     * @param uid
     * @param link
     * @param start
     */
    public KnopDB(long starttime, String uid, ActivityLink link, boolean start){
        UID = uid;
        if(start)
            startTimer(link );
        else
            updateTimer(starttime, link);
    }
    public void updateTimer(long begintijd, ActivityLink link){
        update_entry(begintijd, System.currentTimeMillis(), link.getProjectID(), link.getActivityID());
    }
    public void startTimer(ActivityLink link)
    {
        new_entry(System.currentTimeMillis(), System.currentTimeMillis(), link.getProjectID(), link.getActivityID());
    }
    public void new_entry(long begintijd, long eindtijd, String ProjectID, String ActivityID){
        begintijd_opslag = begintijd;

        final String[] gegevens_online = { Long.toString(begintijd), Long.toString(eindtijd), ProjectID, ActivityID, UID };
        // TODO and fix JavaQL
        final String finalEindTijd = Long.toString(eindtijd);
        final String finalBeginTijd =   Long.toString(begintijd);
        JavaQL update = new JavaQL() {
            @Override
            protected List<ProjectObject> doInBackground(String... strings) {
                Insert("Timer_input", kolommen_online, gegevens_online);
                return null;
            }
        };
        update.execute();
        //JavaQL.Insert("Timer_input", kolommen_online, gegevens_online, false);
        //
    }
    public void update_entry(long starttijd, long eindtijd, String ProjectID, String ActivityID){
        final String finalEindTijd = Long.toString(eindtijd);
        final String finalBeginTijd =   Long.toString(starttijd);
        JavaQL update = new JavaQL() {
            @Override
            protected List<ProjectObject> doInBackground(String... strings) {
                update("Timer_input", "EindTijd", finalEindTijd , "Begintijd",finalBeginTijd);
                return null;
            }
        };
        update.execute();
    }
}

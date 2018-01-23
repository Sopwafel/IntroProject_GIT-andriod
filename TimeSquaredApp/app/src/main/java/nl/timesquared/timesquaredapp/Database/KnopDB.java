package nl.timesquared.timesquaredapp.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by Sopwafel on 23-1-2018.
 */

public class KnopDB {
    long begintijd_opslag;
    String UID;
    static String[] kolommen = { "Begintijd", "Eindtijd", "Project_ID", "Activiteit_ID", "Synced" };
    static String[] kolommen_online = { "Begintijd", "Eindtijd", "Project_ID", "Activiteit_ID", "UserID" };
    public KnopDB(String uid){
        UID = uid;

    }
    public void new_entry(long begintijd, long eindtijd, String ProjectID, String ActivityID){
        begintijd_opslag = begintijd;
        // TODO figure out what format the server needs
        String[] gegevens_online = { Long.toBinaryString(begintijd), Long.toBinaryString(eindtijd), ProjectID, ActivityID, UID };
        // TODO and fix JavaQL
        //JavaQL.Insert("Timer_input", kolommen_online, gegevens_online, false);
    }
    public void update_entry(long starttijd, long eindtijd){
        final String endtijd = Long.toBinaryString(eindtijd);
        final String bugintijd =   Long.toBinaryString(starttijd);
        JavaQL survur = new JavaQL() {
            @Override
            protected List<Object> doInBackground(String... strings) {
                ServerConnection();
                update("Timer_input", "EindTijd", endtijd , "Begintijd",bugintijd);
                return null;
            }
        };
    }
}

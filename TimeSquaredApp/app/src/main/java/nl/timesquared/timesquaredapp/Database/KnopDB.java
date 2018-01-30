package nl.timesquared.timesquaredapp.Database;

import java.util.List;

import nl.timesquared.timesquaredapp.Objects.ActivityLink;
import nl.timesquared.timesquaredapp.Objects.ProjectObject;

/**
 * Created by Sopwafel on 23-1-2018.
 */

public  class KnopDB {
    /**
     * UserID for talking to the server
     */
    String UID;
    /**
     * SQL needs these to talk to the server
     */
    static String[] kolommen_online = { "Begintijd", "Eindtijd", "Project_ID", "Activiteit_ID", "UserID" };

    /**
     * This is so ugly but Java doesn't allow static base classes.
     * INSERTs or UPDATEs a timerInput
     * @param starttime
     * @param uid
     * @param link ActivityLink to start timer of
     * @param start true if INSERT, false if UPDATE
     */
    public KnopDB(long starttime, String uid, ActivityLink link, boolean start){
        UID = uid;
        if(start)
            startTimer(link, starttime);
        else
            updateTimer(starttime);
    }

    /**
     * Changes the epoch millis into .NET time.
     * We need this because the desktop millis start from 0001-1-1 and Java from 1970-1-1
     * @param input epoch millis
     * @return .NEt millis
     */
    private long makeDotNETTime(long input){
        // This method took me over an hour. Super annoying.
        // After getting epoch millis in c# there still was a difference between C# and Java time in the database.
        // diff is that difference
        long diff = 5248215141696096985l-5233043503742918979l;
        // We got the number below from c#
        return input + 5233041986427387904l + diff;

    }

    /**
     * Simply calls update_entry so it updates an entry so endTime is currentTime
     * @param begintijd
     */
    public void updateTimer(long begintijd){
        // Check if time is longer than 8 hours. Maybe user left timer on overnight
        if(System.currentTimeMillis()-begintijd<28800000)
            update_entry(makeDotNETTime(begintijd), makeDotNETTime(System.currentTimeMillis()));
    }

    /**
     * Simply calls new_entry so it makes a new entry
     * @param link
     */
    public void startTimer(ActivityLink link, long startTime)
    {
        new_entry(makeDotNETTime(startTime), makeDotNETTime(System.currentTimeMillis()), link.getProjectID(), link.getActivityID());
    }

    /**
     * Uses JavaQL to creates a new entry in the server
     * @param begintijd
     * @param eindtijd
     * @param ProjectID
     * @param ActivityID
     */
    public void new_entry(long begintijd, long eindtijd, String ProjectID, String ActivityID){
        final String[] gegevens_online = { Long.toString(begintijd), Long.toString(eindtijd), ProjectID, ActivityID, UID };
        JavaQL update = new JavaQL() {
            @Override
            protected List<ProjectObject> doInBackground(String... strings) {
                Insert("Timer_input", kolommen_online, gegevens_online);
                return null;
            }
        };
        update.execute();
    }

    /**
     * Uses JavaQL to update an entry to have a new endTime.
     * Only needs startTime because SQL only needs that
     * @param starttijd
     * @param eindtijd
     */
    public void update_entry(long starttijd, long eindtijd){
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

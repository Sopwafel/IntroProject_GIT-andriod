package nl.timesquared.timesquaredapp.Objects;

import java.sql.Date;

/**
 * Created by Sopwafel on 22-1-2018.
 */

public abstract class TimedEvent extends Entry{
    protected long startTime;
    protected long endTime;

    public long StartTime(){
        return startTime;
    }
    public long EndTime(){
        return endTime;

    }
    public TimedEvent(long startTime, long endTime){
        this.startTime=startTime;
        this.endTime=endTime;
    }
}

package nl.timesquared.timesquaredapp.Database;

import android.util.Log;

import nl.timesquared.timesquaredapp.Objects.Entry;
import nl.timesquared.timesquaredapp.Objects.InputEvent;

/**
 * Created by Sopwafel on 22-1-2018.
 */

public class Timer implements Runnable {
    InputEvent timer;
    public Timer(Object o){
        timer = InputEvent.class.cast(o);
    }
    public void run(){
            try{
                //TODO eventwriter werkt dus nog niet
                EventWriter.UpdateEvent(timer, System.currentTimeMillis());
                Thread.sleep(600000);}
            catch(Exception ex){
                Log.d("timer", "Sleep interuppted");
            }

    }
}

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
                // TODO eventwriter werkt dus nog niet
                // TODO en uiteindelijk willen we dit in een persistente background thread laten draaien met een persistent notification om de timer uit te zetten
                EventWriter.UpdateEvent(timer, System.currentTimeMillis());
                Thread.sleep(600000);}
            catch(Exception ex){
                Log.d("timer", "Sleep interuppted");
            }

    }
}

package nl.timesquared.timesquaredapp.Objects;

import android.content.Context;
import android.widget.Button;

import org.w3c.dom.NameList;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Sopwafel on 18-1-2018.
 */

public abstract class NamedEntry extends Entry implements Serializable {
    private String ID;
    public String getID(){
        return ID;
    }
    private String name;
    public String getName(){
        return name;
    }
    public String toString(){
        return name;
    }
    public NamedEntry(String ID, String Name, int color)
    {
        super(color);
        this.ID=ID;
        this.name= Name;
    }

}

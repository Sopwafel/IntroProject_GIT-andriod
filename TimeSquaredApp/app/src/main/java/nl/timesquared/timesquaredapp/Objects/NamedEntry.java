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
    public int order;
    private UUID ID;
    public UUID getID(){
        return ID;
    }
    private String name;
    public String getName(){
        return name;
    }
    public String toString(){
        return name;
    }
    public NamedEntry(UUID ID, String Name, int color, int order)
    {
        super(color);
        this.ID=ID;
        this.name= Name;
        this.order = order;
    }

}

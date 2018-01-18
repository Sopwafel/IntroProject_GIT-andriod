package nl.timesquared.timesquaredapp.Objects;

import org.w3c.dom.NameList;

import java.util.UUID;

/**
 * Created by Sopwafel on 18-1-2018.
 */

public abstract class NamedEntry extends Entry{
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

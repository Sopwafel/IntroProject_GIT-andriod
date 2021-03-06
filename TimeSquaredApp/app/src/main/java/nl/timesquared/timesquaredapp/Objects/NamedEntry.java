package nl.timesquared.timesquaredapp.Objects;

/**
 * Created by Sopwafel on 18-1-2018.
 * Base class of all namedEntries(which is all non-timer entries. Basically everything you want to give a name to)
 */

public abstract class NamedEntry extends Entry {
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

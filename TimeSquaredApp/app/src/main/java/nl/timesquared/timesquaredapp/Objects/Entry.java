package nl.timesquared.timesquaredapp.Objects;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Sopwafel on 18-1-2018.
 * Base class of all dataObjects
 */

public abstract class Entry implements Serializable {
    public int color = Color.WHITE;
    public Entry(int color)
    {
        this.color = color;
    }
    public Entry()
    {
    }

}

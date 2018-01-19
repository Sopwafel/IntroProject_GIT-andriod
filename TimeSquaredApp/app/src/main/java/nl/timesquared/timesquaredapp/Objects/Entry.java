package nl.timesquared.timesquaredapp.Objects;

import android.graphics.Color;

/**
 * Created by Sopwafel on 18-1-2018.
 */

public abstract class Entry {
    public int color = Color.WHITE;
    public Entry(int color)
    {
        this.color = color;
    }
    public Entry()
    {
    }

}

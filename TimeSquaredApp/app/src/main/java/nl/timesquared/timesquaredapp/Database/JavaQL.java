package nl.timesquared.timesquaredapp.Database;

import android.os.AsyncTask;

import java.io.Serializable;
import java.net.URL;
import android.util.Log;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sopwafel on 16-1-2018.
 */

public abstract class JavaQL extends AsyncTask<String, String, List<Object>> {

    Connection connection = null;
    Statement stmt;

    /**
     * Makes a Connection and puts it into the connection field
     */
    void ServerConnection()
    {
        String hostName = "timesquared.database.windows.net:1433";
        String dbName = "timesquared_db";
        String user = "teamZeta";
        String password = "SuperVincent@";
        String url =String.format("jdbc:jtds:sqlserver://%s;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password) ;
        try {
            Connection connection = DriverManager.getConnection(url);
            Log.d("click", "We push button");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(connection != null) try {connection.close(); Log.d("connection","connection null: closing connection");} catch (Exception e){}
        }
    }

    /**
     * Puts datain in kolommen in Tabelnaam in the server
     * @param Tabelnaam
     * @param kolommen
     * @param datain
     */
    public void Insert(String Tabelnaam, String[] kolommen, String[] datain){
        String insertstring = "INSERT INTO " + Tabelnaam + " (";

        String[] data = new String[kolommen.length];
        if (datain.length != kolommen.length)
        {
            data = new String[kolommen.length];
            for(int i =0; i<kolommen.length; i++)
            {
                try
                {
                    data[i] = datain[i];
                }
                catch(Exception e)
                {
                    data[i] = "null";
                }
            }
        }
        else
        {
            data = datain;
        }

        for (int i = 0; i < kolommen.length - 1; i++)
        {
            insertstring += kolommen[i] + ", ";
        }

        insertstring += kolommen[kolommen.length - 1] + ") ";
        insertstring += "VALUES (";

        for (int i = 0; i < data.length - 1; i++)
        {

            insertstring += IntOrString(data[i])+ ", ";
        }

        insertstring += IntOrString(data[data.length - 1]) + ");";

        querryExecuter(insertstring);
    }

    /**
     * Helper function for insert
     * Does something that is neccesary to a string
     * @param input
     * @return
     */
    public static String IntOrString(String input)
    {
        if (input != null)
        {
            String result = "";
            if (input.contains("PARSE_INT"))
                result += "'" + input.replace("PARSE_INT:", "")+ "'";
            else
                result += "'" + input.replace("'", "''") + "'";

            return result;
        }
        return "null";
    }

    public void querryExecuter(String querry)
    {
        ServerConnection();
        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(querry);

        }
        catch (Exception e)
        {
            Log.d("querryExecuter", "failed: " +querry);
        }
    }

    /**
     * Requests things from the database and returns them in a list
     * @param wat
     * @param tabel
     * @param tabellen_voorwaarden
     * @param voorwaarden
     * @param objecttype
     * @return
     */
    public List<Object> Select(String[] wat, String tabel, String[] tabellen_voorwaarden, String[] voorwaarden, String objecttype){
        String selectstring = SelectStringMaker("SELECT *", tabel, tabellen_voorwaarden, voorwaarden);
        ArrayList<Object> output = new ArrayList<Object>();
        ServerConnection();
        try {
            stmt = connection.createStatement();
            //OK SO
            //We make a connection
            //Then we make an (empty) statement
            //Then we execute a query from a string with the line below
            ResultSet rs = stmt.executeQuery(selectstring);
            while(rs.next()){

            }
        }
        catch (Exception e){}
        return output;
    }

    /**
     * Makes a string that can be executed
     * @param SelectOfDelete
     * @param tabel
     * @param kolommen_voorwaarden
     * @param voorwaarden
     * @return
     */
    public String SelectStringMaker(String SelectOfDelete, String tabel, String[] kolommen_voorwaarden, String[] voorwaarden/*, string eqorin = "=" , string InTabel = null, string[] voorwaardenIN = null*/ )
    {

        String CommandString = SelectOfDelete + " ";

        if (SelectOfDelete == "select")
            CommandString += "*" + " FROM ";
        else
            CommandString += " FROM ";

        CommandString += tabel + " ";

        if (kolommen_voorwaarden.length >= 1)
        {
            CommandString += "WHERE ";

            for (int i = 0; i < kolommen_voorwaarden.length-1; i++)
            {

                CommandString += kolommen_voorwaarden[i] + " LIKE " + "'" + voorwaarden[i].replace("'", "''") +"'";
                if(i < kolommen_voorwaarden.length-1)
                    CommandString += " AND ";
            }
            CommandString += kolommen_voorwaarden[kolommen_voorwaarden.length - 1] + " LIKE " +"'" + voorwaarden[kolommen_voorwaarden.length - 1] + "'";
        }
        return CommandString;


    }

    /**
     * Casts all items in a list to a different type
     * @param srcList List
     * @param clas Class
     * @param <T> Type
     * @return Converted list
     */
    public <T>List<T> castCollection(List srcList, Class<T> clas){
        List<T> list =new ArrayList<T>();
        for (Object obj : srcList) {
            if(obj!=null && clas.isAssignableFrom(obj.getClass()))
                list.add(clas.cast(obj));
        }
        return list;
    }
}

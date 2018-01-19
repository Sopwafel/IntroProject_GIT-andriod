package nl.timesquared.timesquaredapp.Database;

import android.os.AsyncTask;
import java.net.URL;
import android.util.Log;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import com.microsoft.sqlserver.jdbc.*;



/**
 * Created by Sopwafel on 16-1-2018.
 */

public class JavaQL extends AsyncTask<String, String, String> {
    protected String doInBackground(String... params)
    {
        ServerConnection();
        return "";
    }
    Connection connection = null;

    private void ServerConnection()
    {
        String hostName = "timesquared.database.windows.net:1433";
        String dbName = "timesquared_db";
        String user = "teamZeta";
        String password = "SuperVincent@";
        String url =String.format("jdbc:jtds:sqlserver://%s;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password) ;
        try {
            connection = DriverManager.getConnection(url);
            Log.d("click", "We push button");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(connection != null) try {connection.close();} catch (Exception e){}
        }
    }
}

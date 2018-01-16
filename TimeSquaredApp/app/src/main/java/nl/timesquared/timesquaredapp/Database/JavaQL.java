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
        String hostName = "server=timesquared.database.windows.net";
        String dbName = "timesquared_db";
        String user = "endUser";
        String password = "CaJoKeMaVi*112358";
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        String url2 =String.format("jdbc:sqlserver://timesquared.database.windows.net:1433;database=timesquared_db;user=%s;password={%s};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", user, password) ;
        try {
            connection = DriverManager.getConnection(url2);
/*
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 20 pc.Name as CategoryName, p.name as ProductName "
                    + "FROM [SalesLT].[ProductCategory] pc "
                    + "JOIN [SalesLT].[Product] p ON pc.productcategoryid = p.productcategoryid";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                // Print results from select statement
                System.out.println("Top 20 categories:");
                while (resultSet.next())
                {
                    System.out.println(resultSet.getString(1) + " "
                            + resultSet.getString(2));
                }
                connection.close();
            }*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

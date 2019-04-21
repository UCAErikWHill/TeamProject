package database;

import java.io.*;
import java.sql.*;
import java.util.*;
 
public class Database 
{
  
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    public Database() 
    {

        Properties prop = new Properties();
        FileInputStream fis = null;
 
        try {
            fis = new FileInputStream("Database/db.properties");
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("username");
            String pass = prop.getProperty("password");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }
 
    public void setConnection(String fn) throws Exception 
    {

        Properties prop = new Properties();
        FileInputStream fis = null;
 
        fis = new FileInputStream(fn);
        prop.load(fis);
        String url = prop.getProperty("url");
        String user = prop.getProperty("username");
        String pass = prop.getProperty("password");
        conn = DriverManager.getConnection(url, user, pass);
    }
 
    public Connection getConnection() 
    {
        return conn;
    }
 
    public boolean writeUserToDatabase(String username, String password) 
    {
 
        String query = "INSERT INTO user VALUES ('" + username.toString() + "',aes_encrypt('" + password.toString() + "','key'), 0);";
 
        try 
        {
            stmt = conn.createStatement();
            stmt.execute(query);
            rs = stmt.getResultSet();
            
        } catch (SQLException e) 
        {
            e.printStackTrace();
        } catch (NullPointerException e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
 
        return true;
    }
 
    public boolean usernameInUse(String username) 
    {
 
        String dbUsername;
        boolean user = false;
        String query = "SELECT username FROM user;";
 
        try 
        {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
 
            while (rs.next()) {
                dbUsername = rs.getString("username");
                if (dbUsername.equalsIgnoreCase(username))
                    user = true;
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        } catch (NullPointerException e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        return user;
    }
 
    public boolean loginSuccessful(String username, String password) 
    {
 
        String dbUsername, dbPassword;
        //String decrypt = "aes_decrypt('" + password.toString() + "','key')";
        boolean user = false;
        String query = "SELECT username, aes_decrypt(password,'key') FROM user;";
 
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
 
            while (rs.next()) {
                dbUsername = rs.getString("username"); 
                dbPassword = rs.getString("aes_decrypt(password,'key')");
 
                if (dbUsername.equalsIgnoreCase(username) && dbPassword.equals(password))
                    user = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }
 
    public int getScore(String username) 
    {
 
        String dbUsername;
        int dbScore;
        String query = "SELECT username, score FROM user;";
 
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
 
            while (rs.next()) {
                dbUsername = rs.getString("username");
                dbScore = rs.getInt("score");
                if (dbUsername.equalsIgnoreCase(username))
                    return dbScore;
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        } catch (NullPointerException e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
 
        System.out.println("Get Score Failed");
        return 0;
    }
 
    public void updateScores(String username, int score) 
    {
 
        String query = "UPDATE user SET score = " + (score += 1) + " WHERE username = '" + username + "';";
 
        try 
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        System.out.println("Update Success");
    }
 
    public ArrayList<String> query(String query) 
    {
        ArrayList<String> arrayList = new ArrayList<String>();
 
        try 
        {
            stmt = conn.createStatement();
            stmt.execute(query);
            rs = stmt.executeQuery(query);
 
            while (rs.next()) 
            {
                String columns = rs.getString(1) + "," + rs.getString(2);
                arrayList.add(columns);
            }
            conn.close();
        } catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
 
        if (arrayList.isEmpty())
            return null;
        return arrayList;
    }
}
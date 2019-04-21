package serverComm;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import clientComm.Leaderboard;
import database.Database;
import serverComm.Error;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServer extends AbstractServer{
   // Data fields for this chat server.
    private JTextArea log;
    private JLabel status;
    private boolean running = false;
    //private DatabaseFile database = new DatabaseFile();
    private Database database = new Database();

    // Constructor for initializing the server with default settings.
    public ChessServer()
    {
      super(12345);
      this.setTimeout(500);
    }

    // Getter that returns whether the server is currently running.
    public boolean isRunning()
    {
      return running;
    }
    
    // Setters for the data fields corresponding to the GUI elements.
    public void setLog(JTextArea log)
    {
      this.log = log;
    }
    public void setStatus(JLabel status)
    {
      this.status = status;
    }

    // When the server starts, update the GUI.
    public void serverStarted()
    {
      running = true;
      status.setText("Listening");
      status.setForeground(Color.GREEN);
      log.append("Server started\n");
    }
    
    // When the server stops listening, update the GUI.
     public void serverStopped()
     {
       status.setText("Stopped");
       status.setForeground(Color.RED);
       log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
     }
   
    // When the server closes completely, update the GUI.
    public void serverClosed()
    {
      running = false;
      status.setText("Close");
      status.setForeground(Color.RED);
      log.append("Server and all current clients are closed - press Listen to restart\n");
    }

    // When a client connects or disconnects, display a message in the log.
    public void clientConnected(ConnectionToClient client)
    {
      log.append("Client " + client.getId() + " connected\n");
    }

    // When a message is received from a client, handle it.
    public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
    {
      Object result = null;
      // If we received LoginData, verify the account information.
      if (arg0 instanceof LoginData)
      {
        // Check the username and password with the database.
        LoginData data = (LoginData)arg0;
        if(database.loginSuccessful(data.getUsername(), data.getPassword()))
        {
          result = "LoginSuccessful";
          log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
        }
        else
        {
          result = new Error("The username and password are incorrect.", "Login");
          log.append("Client " + arg1.getId() + " failed to log in\n");
        }
        //        ArrayList<String> r = new ArrayList<String>();
       // Object result;
//        String test;
//        String command = "select username from user where username="+ data.getUsername() +" and ase_decrypt(password, 'key') =" + data.getPassword();
//        r = database.query(command);
//        if (r != null)
//        {
//            result = "LoginSuccessful";
//                log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
//        }
//        else
//        {
//          result = new Error("The username and password are incorrect.", "Login");
//            log.append("Client " + arg1.getId() + " failed to log in\n");
//        }
        
        // Send the result to the client.
        try
        {
          arg1.sendToClient(result);
        }
        catch (IOException e)
        {
          return;
        }
      }
      
      // If we received CreateAccountData, create a new account.
      else if (arg0 instanceof CreateAccountData)
      {
        // Try to create the account.
        CreateAccountData data = (CreateAccountData)arg0;
       // Object result;
        if(!database.usernameInUse(data.getUsername()) && data.getPassword().equals(data.getPassword2()))
        {
          database.writeUserToDatabase(data.getUsername(), data.getPassword());
          result = "CreateAccountSuccessful";
          log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
        }
        else
        {
          log.append("Client " + arg1.getId() + " failed to create a new account\n");
          result = new Error("The username is already in use/Passwords don't match", "CreateAccount");
        }
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
//        String command = "select username from user where username=?";
//        String insert = "insert into user values('" + data.getUsername()+ ",aes_encrypt('"+ data.getPassword() +"','key'))";
//    ArrayList<String> r = new ArrayList<String>();
//    try {
//      r = database.query(command);
//    } catch (SQLException e1) {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//    }
//      if (r != null)
//        {
//          for (String row : r)
//          {
//            if(row.equals(data.getUsername())) {
//              result = new Error("The username is already in use.", "CreateAccount");
//                log.append("Client " + arg1.getId() + " failed to create a new account\n");
//                try
//                {
//                  arg1.sendToClient(result);
//                }
//                catch (IOException e)
//                {
//                  return;
//                }
//            }
//            try {
//        database.executeDML(insert);
//      } catch (SQLException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//          result = "CreateAccountSuccessful";
//            log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
//        
//      }
        if (arg0 instanceof LeaderboardData) {
          String comm = "select * from leaderboard";
          r = database.query(comm);
          
          try
          {
            arg1.sendToClient(result);
          }
          catch (IOException e)
          {
            return;
          }
          
        }
        if(arg0 instanceof BoardData) {
          try
          {
            arg1.sendToClient(data);
          }
          catch (IOException e)
          {
            return;
          }
        }
        if (arg0 instanceof StatusData) {
          String comm = "select * from ";
        }
        // Send the result to the client.
        try
        {
          arg1.sendToClient(result);
        }
        catch (IOException e)
        {
          return;
        }
      } 
      }
    }
    // Method that handles listening exceptions by displaying exception information.
    public void listeningException(Throwable exception) 
    {
      running = false;
      status.setText("Exception occurred while listening");
      status.setForeground(Color.RED);
      log.append("Listening exception: " + exception.getMessage() + "\n");
      log.append("Press Listen to restart server\n");
    }
}
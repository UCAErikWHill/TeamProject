package serverComm;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import clientComm.Leaderboard;
import clientGUI.MainGUI;
import database.Database;
import serverComm.Error;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServer extends AbstractServer{
   // Data fields for this chat server.
    private boolean running = false;
    //private DatabaseFile database = new DatabaseFile();
    private Database database;
    private boolean playerWaiting = false;
    private ConnectionToClient clientWaiting;
    // Constructor for initializing the server with default settings.
    public ChessServer()
    {
      super(8301);
      this.setTimeout(500);
      database = new Database();
    }

    // Getter that returns whether the server is currently running.
    public boolean isRunning()
    {
      return running;
    }

    // When the server starts, update the GUI.
    public void serverStarted()
    {
      running = true;
      System.out.println("Server started");
    }
    
    // When the server stops listening, update the GUI.
     public void serverStopped()
     {
       System.out.println("Server stopped");
     }
   
    // When the server closes completely, update the GUI.
    public void serverClosed()
    {
      running = false;
      System.out.println("Server closed");

    }

    // When a client connects or disconnects, display a message in the log.
    public void clientConnected(ConnectionToClient client)
    {
      System.out.println("Client " + client.getId() + " connected\n");
    }

    // When a message is received from a client, handle it.
    public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
    {
      Object result = null;
      System.out.println("Message from client: " + arg0);
      // If we received LoginData, verify the account information.
      if (arg0 instanceof LoginData)
      {
        // Check the username and password with the database.
        LoginData data = (LoginData)arg0;
        if(database.loginSuccessful(data.getUsername(), data.getPassword()))
        {
          result = "LoginSuccessful";
          System.out.println("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
        }
        else if(data.getUsername().equalsIgnoreCase("admin") && data.getPassword().equals("admin"))
        {
          result = "LoginSuccessfulAdmin";
          System.out.println("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
        }
        else
        {
          result = new Error("The username and password are incorrect.", "Login");
          System.out.println("Client " + arg1.getId() + " failed to log in\n");
        }

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
          System.out.println("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
        }
        else
        {
          System.out.println("Client " + arg1.getId() + " failed to create a new account\n");
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
      }
      else if (arg0 instanceof LeaderboardData) {
          String comm = "select * from leaderboard";          
          try
          {
            arg1.sendToClient(result);
          }
          catch (IOException e)
          {
            return;
          }
          
        }
      else if(arg0 instanceof BoardData) {
          try
          {
            BoardData d = (BoardData) arg0;
            result = d;
            arg1.sendToClient(result);
          }
          catch (IOException e)
          {
            return;
          }
        }
      else if (arg0 instanceof StatusData) {
          String comm = "select * from ";
        
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
      
      else if (arg0 instanceof String)
      {
        String msg = (String) arg0;
        if(msg.equals("newgame"))
        {
          System.out.println("client pressed newgame");
          if(!playerWaiting)
          {
            playerWaiting = true;
            clientWaiting = arg1;
            System.out.println("waiting on another client to join. playerwaiting: " + playerWaiting );
            try
            {
              result = "wait";
              arg1.sendToClient(result);
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          else
          {
            playerWaiting = false;
            //this.sendToAllClients("gamestart");
            try
            {
              result = "gamestart";
              arg1.sendToClient(result);
              clientWaiting.sendToClient(result);
              System.out.println("New game starting");
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
        else
        {
          System.out.println("Game ended. " + msg +" won!");
          int score = database.getScore(msg);
          database.updateScores(msg,score);
        }
      }
    }
    
    // Method that handles listening exceptions by displaying exception information.
    public void listeningException(Throwable exception) 
    {
      running = false;
      System.out.println("Exception occurred while listening");
      System.out.println("Listening exception: " + exception.getMessage() + "\n");
    }
    public static void main(String[] args)
    {
      ChessServer server = new ChessServer();
      try
      {
        server.listen();
      } catch (IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
}

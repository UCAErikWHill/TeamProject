package clientComm;
import clientComm.*;
import ocsf.client.AbstractClient;
import serverComm.Error;

public class MainClient extends AbstractClient
{
  private Login login;
  private Game game;
  private NewAcc newaccount;
  private Option opt;
  private Status stat;
  private Leaderboard leader;
  
  public MainClient()
  {
    super("localhost", 8301);
  }
  
  public MainClient(String host, int port)
  {
    super(host, port);
  }

  public void setLoginControl(Login login)
  {
    this.login = login;
  }
  
  public void setNewAccountControl(NewAcc newaccount)
  {
    this.newaccount = newaccount;
  }
  
  public void setLeaderboardControl(Leaderboard leader)
  {
    this.leader = leader;
  }
  
  public void setOptionControl(Option opt)
  {
    this.opt = opt;
  }
  
  public void setStatusControl(Status stat)
  {
    this.stat = stat;
  }
  
  public void setGameControl(Game game)
  {
    this.game = game;
  }

  // Method that handles messages from the server.
  public void handleMessageFromServer(Object arg0)
  {
    // If we received a String, figure out what this event is.
    if (arg0 instanceof String)
    {
      // Get the text of the message.
      String message = (String)arg0;
      
      // If we successfully logged in, tell the login controller.
      if (message.equals("LoginSuccessful") || message.equals("LoginSuccessfulAdmin"))
      {
        login.loginSuccess();
        if(message.equals("LoginSuccessfulAdmin"))
        {
          opt.setAdmin(true);  
        }
        else
          opt.setAdmin(false);
      }
      
      // If we successfully created an account, tell the create acentcount controller.
      else if (message.equals("CreateAccountSuccessful"))
      {
        newaccount.createAccountSuccess();
      } 
    }
    
    // If we received an Error, figure out where to display it.
    else if (arg0 instanceof Error)
    {
      // Get the Error object.
      Error error = (Error)arg0;
      
      // Display login errors using the login controller.
      if (error.getType().equals("Login"))
      {
        login.displayError(error.getMessage());
      }
      
      // Display account creation errors using the create account controller.
      else if (error.getType().equals("CreateAccount"))
      {
        newaccount.displayError(error.getMessage());
      }
    }
  }  

  
}

package clientGUI;

import javax.swing.*;

import serverComm.LoginData;

import java.awt.*;
import java.io.IOException;
import clientComm.*;
public class MainGUI extends JFrame
{
  
  // Constructor that creates the client GUI.
  public MainGUI()
  {
    // Set up the chat client.
   MainClient client = new MainClient();
    client.setHost("localhost");
    client.setPort(8301);
    try
    {
      client.openConnection();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    
    
    // Set the title and default close operation.
    this.setTitle("Chess Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    // Create the card layout container.
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    
    //Create the Controllers next
    //Next, create the Controllers
    Login login = new Login(container,client);
    Leaderboard leader = new Leaderboard(container,client);
    NewAcc newaccount = new NewAcc(container,client);
    Option opt = new Option(container,client);
    Status stat = new Status(container,client);
    Game game = new Game(container, client);
        
    //Set the client info
    client.setLoginControl(login);
    client.setNewAccountControl(newaccount);
    client.setLeaderboardControl(leader);
    client.setOptionControl(opt);
    client.setStatusControl(stat);
    client.setGameControl(game);
    
    // Create the four views. (need the controller to register with the Panels
    JPanel view1 = new LoginGUI(login);
    JPanel view2 = new LeaderboardGUI(leader);
    JPanel view3 = new NewAccGUI(newaccount);
    JPanel view4 = new OptionGUI(opt);
    JPanel view5 = new StatusGUI(stat);
    JPanel view6 = new BoardGUI(game);
    
    // Add the views to the card layout container.
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    container.add(view4, "4");
    container.add(view5, "5");
    container.add(view6, "6");

    
    // Show the initial view in the card layout.
    cardLayout.show(container, "1");
    
    // Add the card layout container to the JFrame.
    // GridBagLayout makes the container stay centered in the window.
    this.setLayout(new GridBagLayout());
    this.add(container);

    // Show the JFrame.
    this.setSize(550, 350);
    this.setVisible(true);
  }

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args)
  {
    new MainGUI();
  }
}

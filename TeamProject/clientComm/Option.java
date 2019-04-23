package clientComm;


import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import clientComm.MainClient;
import clientGUI.OptionGUI;

public class Option implements ActionListener
{
    // Private data field for storing the container.
    private JPanel container;
    private MainClient client;
    private boolean admin;
    // Constructor for the initial controller.
    public Option(JPanel container, MainClient client)
    {
      this.container = container;
      this.client = client;
    }
    // Handle button clicks.
    public void actionPerformed(ActionEvent ae)
    {
      // Get the name of the button clicked.
      String command = ae.getActionCommand();

      // The Cancel button takes the user back to the initial panel.
      if (command.equals("New Game"))
      {
        try
        {
          Object result = "newgame";
          client.sendToServer(result);
        } catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "6");
      }
      else if (command.equals("See Leaderboard"))
      {
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "2");
      }
      else if (command.equals("Server Status"))
      {
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "5");
      }
    }
    public boolean isAdmin()
    {
      return admin;
    }
    public void setAdmin(boolean b)
    {
      this.admin = b;
      if(b==true)
      {
        OptionGUI gui = (OptionGUI)container.getComponent(3);
        gui.setStatusVisible();
      }
    }
    
}
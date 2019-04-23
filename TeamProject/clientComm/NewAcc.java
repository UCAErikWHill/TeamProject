package clientComm;


import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import clientComm.MainClient;
import clientGUI.*;
import serverComm.CreateAccountData;
public class NewAcc implements ActionListener
{
    // Private data field for storing the container.
    private JPanel container;
    private MainClient client;
    // Constructor for the initial controller.
    public NewAcc(JPanel container, MainClient client)
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
      if (command.equals("Create"))
      {
        NewAccGUI newaccgui = (NewAccGUI)container.getComponent(2);
        CreateAccountData data = new CreateAccountData(newaccgui.getUsername(), newaccgui.getPassword(), newaccgui.getPassword2());
        try
        {
          client.sendToServer(data);
        } catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      else if(command.equals("Back"))
      {
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "1");
      }
    }
    public void displayError(String message)
    {
      NewAccGUI newaccgui = (NewAccGUI)container.getComponent(2);
      newaccgui.setError(message);
    }
    public void createAccountSuccess()
    {
      NewAccGUI newaccountgui = (NewAccGUI)container.getComponent(2);
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }
}
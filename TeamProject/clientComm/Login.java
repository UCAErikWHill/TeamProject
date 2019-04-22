package clientComm;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import clientComm.MainClient;
import clientGUI.LoginGUI;
import serverComm.LoginData;

public class Login implements ActionListener
{
    // Private data field for storing the container.
    private JPanel container;
    private MainClient client;
    // Constructor for the initial controller.
    public Login(JPanel container, MainClient client)
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
      if (command.equals("Submit"))      
      {
        LoginGUI logingui = (LoginGUI)container;
        LoginData data = new LoginData(logingui.getUsername(), logingui.getPassword());
        try
        {
          client.sendToServer(data);
        } catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      else if(command.equals("New Account"))
      {
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "3");
      }
      
    }
    
    // After the login is successful, set the User object and display the contacts screen.
    public void loginSuccess()
    {
      LoginGUI logingui = (LoginGUI)container.getComponent(1);
      
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "4");
    }

    // Method that displays a message in the error label.
    public void displayError(String error)
    {
      LoginGUI logingui = (LoginGUI)container.getComponent(1);
      logingui.setError(error);
    }
}
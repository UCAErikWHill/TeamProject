package clientComm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import clientComm.MainClient;

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
    @Override
    public void actionPerformed(ActionEvent e)
    {
      
    }
}
package clientGUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import clientComm.NewAcc;

public class NewAccGUI extends JPanel
{
  private JTextField usernameField;
  private JLabel userLabel;
  private JPasswordField passwordField;
  private JLabel passLabel;
  private JLabel errorLabel;
  private JPasswordField passwordField2;
  
  public NewAccGUI(NewAcc newaccount)
  {
    JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    errorLabel = new JLabel("", JLabel.CENTER);
    errorLabel.setForeground(Color.RED);
    JLabel instructionLabel = new JLabel("Enter your new account's information.", JLabel.CENTER);
    labelPanel.add(errorLabel);
    labelPanel.add(instructionLabel);

    // Create a panel for the login information form.
    JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
    usernameField = new JTextField(10);
    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
    passwordField = new JPasswordField(10);
    JLabel passwordLabel2 = new JLabel("Re-enter Password:", JLabel.RIGHT);
    passwordField2 = new JPasswordField(10);
    loginPanel.add(usernameLabel);
    loginPanel.add(usernameField);
    loginPanel.add(passwordLabel2);
    loginPanel.add(passwordField2);
    
    // Create a panel for the buttons.
    JPanel buttonPanel = new JPanel();
    JButton createButton = new JButton("Create");
    createButton.addActionListener(newaccount);
    buttonPanel.add(createButton);

    // Arrange the three panels in a grid.
    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
    new GridLayout(3, 1, 0, 10);
    grid.add(labelPanel);
    grid.add(loginPanel);
    grid.add(buttonPanel);
    this.add(grid);

  }
  
  // Getter for the text in the username field.
  public String getUsername()
  {
    return usernameField.getText();
  }
  
  // Getter for the text in the password field.
  public String getPassword()
  {
    return new String(passwordField.getPassword());
  }
  public String getPassword2()
  {
    return new String(passwordField2.getPassword());
  }
  // Setter for the error text.
  public void setError(String error)
  {
    errorLabel.setText(error);
  }
}

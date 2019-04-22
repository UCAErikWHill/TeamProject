package clientGUI;

import javax.swing.JButton;
import javax.swing.JPanel;

import clientComm.Option;

public class OptionGUI extends JPanel
{
  private JButton statusButton;
  public OptionGUI(Option opt)
  {
    JButton newgameButton = new JButton("New Game");
    JButton leaderButton = new JButton("See Leaderboard");
    statusButton = new JButton("Server Status");
    newgameButton.addActionListener(opt);
    leaderButton.addActionListener(opt);
    statusButton.addActionListener(opt);
    statusButton.setVisible(false);
  }
  public void setStatusVisible()
  {
    statusButton.setVisible(true);
  }
}

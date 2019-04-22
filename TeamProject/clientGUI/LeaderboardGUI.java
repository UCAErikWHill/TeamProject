package clientGUI;

import javax.swing.*;

import clientComm.Leaderboard;

public class LeaderboardGUI extends JPanel
{
  public LeaderboardGUI(Leaderboard leader)
  {
    JTextArea leaderboard = new JTextArea();
    JButton back = new JButton("Back");
    back.addActionListener(leader);
  }
}

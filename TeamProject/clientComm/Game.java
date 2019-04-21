package clientComm;

import chesspresso.Chess;
import chesspresso.game.*;
import chesspresso.engines.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

//import chesslib.game.Game;

import clientComm.MainClient;

public class Game implements ActionListener
{
    // Private data field for storing the container.
    private JPanel container;
    private MainClient client;
    // Constructor for the initial controller.
    public Game(JPanel container, MainClient client)
    {
      this.container = container;
      this.client = client;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
      // TODO Auto-generated method stub
      
    }
}
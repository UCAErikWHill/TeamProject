package clientComm;

import chesspresso.Chess;
import chesspresso.game.GameHeaderModel;
import chesspresso.game.GameModel;
import chesspresso.game.GameMoveModel;
import chesspresso.game.view.GameBrowser;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.FEN;
import chesspresso.position.Position;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JPanel;

//import chesslib.game.Game;

import clientComm.MainClient;
import clientGUI.BoardGUI;
import serverComm.BoardData;

public class Game implements ActionListener
{
  // Private data field for storing the container.
  private JPanel container;
  private MainClient client;
  private chesspresso.game.Game chessGame;
  private Position gamePos;
  private int currentPlay;
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
  public void loadGame(BoardData data)
  {
    ByteArrayInputStream bytein = new ByteArrayInputStream(data.getGameBytes());
    DataInputStream in = new DataInputStream(bytein);
    GameModel loadedgamemodel = new GameModel();
    try
    {
      loadedgamemodel.load(in, GameHeaderModel.MODE_STANDARD_TAGS, GameMoveModel.MODE_EVERYTHING);
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    chesspresso.game.Game loadedgame = new chesspresso.game.Game(loadedgamemodel);
    FEN.initFromFEN(loadedgame.getPosition(), data.getBoardFEN(), true);    
    chessGame = loadedgame;
    BoardGUI b = (BoardGUI)container;
    b.redrawBoard(chessGame.getPosition());
  }
  public void addMove(int fromSquareIndex, int toSquareIndex) throws IllegalMoveException 
  {
    
    for (short move : chessGame.getPosition().getAllMoves())
    {
      if(Move.getFromSqi(move) == fromSquareIndex && Move.getToSqi(move) == toSquareIndex)
      {
        if(Move.isPromotion(move))
        {
          if(Move.getPromotionPiece(move) == Chess.QUEEN)
          {
            try
            {
              chessGame.getPosition().doMove(move);
            } catch (IllegalMoveException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
        else 
        {
          try
          {
            chessGame.getPosition().doMove(move);
          } catch (IllegalMoveException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }
  }
  
}
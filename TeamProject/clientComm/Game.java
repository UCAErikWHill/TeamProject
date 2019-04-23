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

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
  private int player;// player can be 0 for 
  private int fromSqi;
  // Constructor for the initial controller.
  public Game(JPanel container, MainClient client)
  {
    this.container = container;
    this.client = client;
    chessGame = new chesspresso.game.Game();
    currentPlay = chessGame.getCurrentPly();
    gamePos = chessGame.getPosition();
    fromSqi = -1;
  }
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    if(currentPlay % 2 == player)
    {
      if(fromSqi == -1)
        fromSqi = Chess.strToSqi(e.getActionCommand());
      else
      {
        try
        {
          this.addMove(fromSqi, Chess.strToSqi(e.getActionCommand()));
        } catch (IllegalMoveException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        fromSqi = -1;
      }
    }
  }
  public void start()
  {
    BoardGUI b = (BoardGUI)container.getComponent(6);
    b.redrawBoard(Position.createInitialPosition());
    b.setVisible(true);
    
  }
  public void end()
  {
    CardLayout cardLayout = (CardLayout)container.getLayout();
    cardLayout.show(container, "4");
    if(gamePos.isMate() && currentPlay % 2 != player)
    {
      try
      {
        Object result = client.getLoggedUsername();
        client.sendToServer(client.getLoggedUsername());
        JOptionPane.showMessageDialog(null, "You Won. Score Increased by 1");
      } catch (IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    else if(gamePos.isMate() && currentPlay % 2 == player)
      JOptionPane.showMessageDialog(null, "You Lost!");
    else if(gamePos.isStaleMate())
      JOptionPane.showMessageDialog(null, "Stalemate/Draw.");
  }
  
  public void setPlayer(int p)
  {
    player = p;
  }
  public int getPlayer()
  {
    return player;
  }
  
  public Position getPos()
  {
    return gamePos;
  }
  public BoardData getGameBoardData()
  {
    ByteArrayOutputStream byteout = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteout);
    ByteArrayInputStream bytein;
    try
    {
      chessGame.getModel().save(out, GameHeaderModel.MODE_STANDARD_TAGS, GameMoveModel.MODE_EVERYTHING);
    } catch (IOException e1)
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    byte[] savedbytes = byteout.toByteArray();
    String fn = chessGame.getPosition().getFEN();
    BoardData save = new BoardData(fn,savedbytes);
    return save;
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
      e.printStackTrace();
    }
    chesspresso.game.Game loadedgame = new chesspresso.game.Game(loadedgamemodel);
    FEN.initFromFEN(loadedgame.getPosition(), data.getBoardFEN(), true);    
    chessGame = loadedgame;
    BoardGUI b = (BoardGUI)container.getComponent(6);
    
    currentPlay = chessGame.getCurrentPly();
    gamePos = chessGame.getPosition();
    if(chessGame.getPosition().isMate() || chessGame.getPosition().isStaleMate())
      this.end();
    else
      b.redrawBoard(chessGame.getPosition());
  }
  
  public void addMove(int fromSquareIndex, int toSquareIndex) throws IllegalMoveException 
  {
    Object result;
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
              currentPlay = chessGame.getCurrentPly();
              gamePos = chessGame.getPosition();
              BoardData dat = this.getGameBoardData();
              result = dat;
              try
              {
                client.sendToServer(dat);
              } catch (IOException e)
              {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
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
            currentPlay = chessGame.getCurrentPly();
            gamePos = chessGame.getPosition();
            BoardData dat = this.getGameBoardData();
            result = dat;
            try
            {
              
              client.sendToServer(dat);
            } catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          } catch (IllegalMoveException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }

    if(chessGame.getPosition().isMate() || chessGame.getPosition().isStaleMate())
      this.end();
  }
}
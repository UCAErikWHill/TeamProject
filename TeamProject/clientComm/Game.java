package clientComm;

import chesspresso.Chess;
import chesspresso.game.view.GameBrowser;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;

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
  private chesspresso.game.Game chessGame;
  private Position gamePos;
  // Constructor for the initial controller.
  public Game(JPanel container, MainClient client)
  {
    this.container = container;
    this.client = client;
    gamePos = Position.createInitialPosition();
    //Move play = new Move(move, move, move, move, false, false, false);
    chessGame = new chesspresso.game.Game();
    chessGame.getPosition().createInitialPosition();
    short move = chessGame.getPosition().getPieceMove(Chess.KING, Chess.sqiToCol(Chess.A1), Chess.sqiToRow(Chess.A1), Chess.A2);
    chessGame.notifyMoveDone(chessGame.getPosition(), move);
  }
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }
  private void addMove(int fromSquareIndex, int toSquareIndex, String comment) throws IllegalMoveException 
  {
    Position position = chessGame.getPosition();
    short [] legalMoves = position.getAllMoves();
    for (int moveIndex = 0; moveIndex < legalMoves.length; moveIndex++){
      short testedMove = legalMoves[moveIndex];
      if (Move.getFromSqi(testedMove) == fromSquareIndex
          && Move.getToSqi(testedMove) == toSquareIndex){
        try {
          position.doMove(testedMove);
          chessGame.addComment(comment);
        } catch (IllegalMoveException e) {
          e.printStackTrace();
        }
        return;
      }
    }
    throw new IllegalMoveException("");
  }
}
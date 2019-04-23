package clientGUI;

import javax.swing.JButton;
import javax.swing.JPanel;

import chesspresso.Chess;
import chesspresso.position.Position;
import clientComm.Game;
public class BoardGUI extends JPanel
{
  private JButton[] boardbuttons;
  public BoardGUI(Game game)
  {
    boardbuttons = new JButton[64];
    for(int i = 0; i<64; i++)
    {
      boardbuttons[i] = new JButton();
      boardbuttons[i].setActionCommand(Chess.sqiToStr(i));
    }
  }
  public void redrawBoard(Position p)
  {
    for(int i = 0; i <64; i++)
    {
      if(p.getStone(i) == Chess.WHITE_PAWN)
        boardbuttons[i].setText("WP");
      if(p.getStone(i) == Chess.WHITE_QUEEN)
        boardbuttons[i].setText("WQ");
      if(p.getStone(i) == Chess.WHITE_KNIGHT)
        boardbuttons[i].setText("WN");
      if(p.getStone(i) == Chess.WHITE_KING)
        boardbuttons[i].setText("WK");
      if(p.getStone(i) == Chess.WHITE_BISHOP)
        boardbuttons[i].setText("WB");
      if(p.getStone(i) == Chess.WHITE_ROOK)
        boardbuttons[i].setText("WR");
      if(p.getStone(i) == Chess.BLACK_PAWN)
        boardbuttons[i].setText("BP");
      if(p.getStone(i) == Chess.BLACK_QUEEN)
        boardbuttons[i].setText("BQ");
      if(p.getStone(i) == Chess.BLACK_ROOK)
        boardbuttons[i].setText("BR");
      if(p.getStone(i) == Chess.BLACK_KNIGHT)
        boardbuttons[i].setText("BN");
      if(p.getStone(i) == Chess.BLACK_BISHOP)
        boardbuttons[i].setText("BB");
      if(p.getStone(i) == Chess.BLACK_KING)
        boardbuttons[i].setText("BK");
    }
  }
}

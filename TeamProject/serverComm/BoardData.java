package serverComm;
import java.io.Serializable;

public class BoardData implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -6277146973157084459L;
  private String boardFEN;
  private byte[] gameBytes;
  
  public BoardData(String fen, byte[] game)
  {
    setGameBytes(game);
    setBoardFEN(fen);
  }
  
  public byte[] getGameBytes()
  {
    return gameBytes;
  }
  
  public String getBoardFEN()
  {
    return boardFEN;
  }
  
  public void setGameBytes(byte[] b)
  {
    gameBytes = b;
  }
  public void setBoardFEN(String f)
  {
    boardFEN = f;
  }
}

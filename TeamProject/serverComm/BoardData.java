package serverComm;
import java.io.Serializable;

public class BoardData implements Serializable
{
  public String boardFEN;
  public byte[] gameBytes;
  
  public BoardData(String fen, byte[] game)
  {
    boardFEN = fen;
    gameBytes = game;
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

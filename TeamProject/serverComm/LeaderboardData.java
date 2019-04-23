package serverComm;
import java.io.Serializable;
import java.util.ArrayList;

public class LeaderboardData implements Serializable
{

  private ArrayList<String> users;
  
  public LeaderboardData()
  {
    users = new ArrayList<String>();
  }
  
  public ArrayList<String> getUsers()
  {
    return users;
  }
  
  public void setUsers(ArrayList<String> s)
  {
    users = s;
  }
  
  public void addUser(String s)
  {
    users.add(s);
  }
  
  public void removeUser(String s)
  {
    int i = 0;
    for(String u : users)
    {
      if(u.equals(s))
      {
        users.remove(i);
      }
      i++;
    }
  }
}

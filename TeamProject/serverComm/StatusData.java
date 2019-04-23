package serverComm;
import java.io.Serializable;
import java.util.ArrayList;

public class StatusData implements Serializable
{
  private ArrayList<String> users;
  
  public StatusData()
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

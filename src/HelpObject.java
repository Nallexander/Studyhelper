import

public class HelpObject{
  private String courseName;
  private String title;
  private String message;
  private String location;
  private String userName;
  private String other;
  private boolean claimed;
  
  public HelpObject(String courseName, String title, String message, String location, String userName, String other){
    self.courseName = courseName;
    self.title = title;
    self.message = message;
    self.location = location;
    if(userName != NULL){
    self.userName = userName:
    }
    else
      self.userName = "Anonymous";
    self.other = other;
    claimed = 0;
  }

  public void printInfo(HelpObject o){
    //TODO Printa grejer snyggt 
  }
  public void claim(boolean claim){
    self.claimed = claim;
    //TODO skicka en callback
  }
  
}

// importa relevanta saker jag vet inte vad
X
public class HelpObject{
  private String courseName;
  private String title;
  private String message;
  private String location;
  private String userName;
  private String other;
  private String clientAddress;
  private boolean claimed;
  
  public HelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress){
    this.courseName = courseName;
    this.title = title;
    this.message = message;
    this.location = location;
    if(userName != NULL){
    this.userName = userName:
    }
    else
      this.userName = "Anonymous";
    this.other = other;
    claimed = 0;
    this.clientAddress = clientAddress;
  }

  public void printInfo(HelpObject o){
    //TODO Printa grejer snyggt 
  }
  public void claim(boolean claim){
    if(claim == 0)
    this.claimed = claim;
    //TODO skicka en callback kanske
  }
  
}

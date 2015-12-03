// importa relevanta saker jag vet inte vad
public class HelpObject{
  private String courseName;
  private String title;
  private String message;
  private String location;
  private String userName;
  private String other;
  private String clientAddress;
  private boolean claimed;
  private String ip;
  
  public HelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress){
    this.courseName = courseName;
    this.title = title;
    this.message = message;
    this.location = location;
    if(userName != ""){
	this.userName = userName;
    }
    else
      this.userName = "Anonymous";
    this.other = other;
    claimed = false;
    this.clientAddress = clientAddress;
  }

  public String BasicInfoString(HelpObject o){
      String course = o.courseName;
      String sub = o.title;
      String info = "Course: " + course + "Subject: " + sub;
      return info;
  }

  public String ExtendInfoString(HelpObject o){
      String course = o.courseName;
      String sub = o.title;
      String 

  }

  public void claim(boolean claim){
    if(claim == false)
    this.claimed = claim;
    //TODO skicka en callback kanske
  }
  
}

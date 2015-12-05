// importa relevanta saker jag vet inte vad
public class HelpObject{
  private String courseName;
  private String title;
  private String message;
  private String location;
  private String userName;
  private String other;
  private String clientAddress;
  private String questionID;
  private boolean claimed;
  
  public HelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress, String questionID){
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
    this.claimed = false;
    this.clientAddress = clientAddress;
    this.questionID =questionID;
  }

  public String basicInfoString(){
	  String qID = this.questionID;
      String course = this.courseName;
      String sub = this.title;
      String info = "ID"+ qID+ "Course: " + course + " Subject: " + sub;
      return info;
  }

  public String extendedInfoString(){
      String exInfo = "ID"+ this.questionID + "\n\n"+ "IP"+ this.clientAddress + "\n\n" + " Course: " + this.courseName + "\n\n" + "Subject: " + this.title + "\n\n" + this.message + "\n\n" + "User: " + this.userName + "\n\n" + "Location: " + this.location + "\n\n" + "Other: " + this.other + "\n\n";
      return exInfo;
  }

  public String getIP(){
      String IP = this.clientAddress;
      return IP;
    }

  public void claim(boolean claim){
    if(claim == false)
    this.claimed = claim;
    //TODO skicka en callback kanske
  }
  
}


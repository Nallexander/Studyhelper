// importa relevanta saker jag vet inte vad
public class HelpObject{
  private String courseName;
  private String title;
  private String message;
  private String location;
  private String userName;
  private String other;
  private String clientAddress;
  private String claimAddress;
  private String questionID;
  private boolean claimed;
  private boolean polled;
  
  public HelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress,String questionID){
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
    this.polled = false;
    this.claimAddress = "";
  }


  
  public String basicInfoString(){
	  String taken = "No";
	  String qID = this.questionID;
      String course = this.courseName;
      String sub = this.title;
      if (this.claimed){
      taken = "Yes";
      }
      String info = "ID: "+ qID + " Claimed: " + taken + " Course: " + course + " Title: " + sub + "\n";
      return info;
  }
  public String getQuestionID(){
	  return this.questionID;
  }
  public String getClientAddress(){
    return this.clientAddress;
  }
  public String getClaimAddress(){
	    return this.claimAddress;
	  }
  public boolean isClaimed(){
	  return this.claimed;
  }
  public synchronized boolean isPolled(){
    return this.polled;
      }
  public synchronized void setPolledTrue(){
    this.polled = true;
  }
  public String extendedInfoString(){
	  String taken = "No";
	  if (this.claimed){
	      taken = "Yes";
	      }
      String exInfo = " ID "+ this.questionID + "\n\n" +" Claimed: " + taken + "\n\n"+ "\n\n" + " Course: " + this.courseName + "\n\n" + " Subject: " + this.title + "\n\n" + " Question: " + this.message + "\n\n" + "User: " + this.userName + "\n\n" + "Location: " + this.location + "\n\n" + "Other: " + this.other + "\n\n";
      return exInfo;
  }

  public String getIP(){
      String IP = this.clientAddress;
      return IP;
    }
  public void setClaimAddress(String address){
	  this.claimAddress = address;
  }
  public void claim(boolean claim){
    //if(claim == false)
    this.claimed = claim;
    //TODO skicka en callback kanske
  }
  
}


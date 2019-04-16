package eg.com.iti.triporganizer.model;

public class UserDTO {
    //----------------salma start-----------------------
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPhoneNum;

    public UserDTO() {
      this.userName=null;
      this.userEmail=null;
      this.userPassword=null;
      this.userPhoneNum=null;
    }

    public UserDTO(String userName, String userEmail, String userPassword, String userPhoneNum) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }
}

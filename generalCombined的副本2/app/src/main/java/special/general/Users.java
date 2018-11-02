package special.general;


public class Users {
    public String userId;
    public String userName;
    public String userPassword;
    public String email;
    public int userScore;
    public String currentUserName;
    public int userEnergy;
    public Users(){

    }

    public Users(String userId, String userName, String userPassword, String email, int userScore, int userEnergy) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.userScore = userScore;
        this.userEnergy = userEnergy;
    }

    public Users updateUser() {
        Users userTem = new Users();

        return userTem;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getEmail() {
        return email;
    }

    public int getUserScore() {
        return userScore;
    }

    public int getUserEnergy() {
        return userEnergy;
    }
}

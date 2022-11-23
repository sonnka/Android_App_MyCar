package nure.kazantseva.mycar.model;

public class UserAuto {

    private String userEmail;
    private int autoId;

    public UserAuto() {
    }

    public UserAuto(String userEmail, int autoId) {
        this.userEmail = userEmail;
        this.autoId = autoId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }
}

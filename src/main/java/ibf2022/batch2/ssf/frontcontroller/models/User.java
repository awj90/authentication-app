package ibf2022.batch2.ssf.frontcontroller.models;

import jakarta.validation.constraints.Size;

public class User {
    
    @Size(min=2, message="Username is too short")
    private String username;

    @Size(min=2, message="Password is too short")
    private String password;

    private int remainingAttempts = 3;

    private int captchaAnswer;

    private boolean authenticated = false;;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(int captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public void decrementRemainingAttempts() {
        this.remainingAttempts--;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}

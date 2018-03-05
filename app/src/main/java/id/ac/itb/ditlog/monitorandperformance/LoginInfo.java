package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public LoginInfo() {
        username = "";
        password = "";
    }

    public void setUsername(String u){
        username = u;
    }

    String getUsername(){
        return username;
    }

    public void setPassword(String p){
        password = p;
    }
}

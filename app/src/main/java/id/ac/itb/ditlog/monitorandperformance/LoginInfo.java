package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo() {
        username = "";
        password = "";
    }

    public void setUsername(String u){
        username = u;
    }

    public void setPassword(String p){
        password = p;
    }

    String getUsername(){
        return username;
    }

    String getPassword(){
        return password;
    }
}

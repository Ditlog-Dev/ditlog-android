package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
    @JsonProperty("userID")
    private String userID;
    @JsonProperty("token")
    private String token;

    public Payload() {
        userID = "";
        token = "";
    }

    public String getUserID(){
        return userID;
    }

    public String getToken(){
        return token;
    }

    //test
    public void setUserID(String s){
        userID = s;
    }
    public void setToken(String s){
        token = s;
    }
}

package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}

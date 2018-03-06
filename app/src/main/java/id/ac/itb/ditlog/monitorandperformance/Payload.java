package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
//    @JsonProperty("userID")
    private long userID;
//    @JsonProperty("token")
    private String token;

    public Payload(Long id, String t) {
        userID = id;
        token = t;
    }

    public long getUserID(){
        return userID;
    }

    public String getToken(){
        return token;
    }
}

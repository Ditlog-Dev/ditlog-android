package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    @JsonProperty("success")
    private String success;
    @JsonProperty("statuscode")
    private String statusCode;
    @JsonProperty("payload")
    private Payload payload;

    public LoginResponse() {
        success = "";
        statusCode = "";
        payload = new Payload();
    }

    public String getSuccess(){
        return success;
    }

    public String getStatusCode(){
        return statusCode;
    }

    public String getUserID(){
        return payload.getUserID();
    }

    public String getToken(){
        return payload.getToken();
    }


    //test
    public void setSuccess(String s){
        success = s;
    }

    public void setUserID(String s){
        payload.setUserID(s);
    }
    public void setToken(String s){
        payload.setToken(s);
    }



}

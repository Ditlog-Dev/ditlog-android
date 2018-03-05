package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        payload = null;
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
}
